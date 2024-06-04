package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.API;
import entity.Carrito;
import entity.Producto;
import entity.ProductoUserAdapter;
import api.UtilREST;

public class UserProductosActivity extends AppCompatActivity {

    private ProductoUserAdapter productoUserAdapter;
    ListView lstProductos;
    List<Producto> listaProductos;
    Carrito carrito;

    Button btnVolver,btnCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_productos);

        btnVolver = findViewById(R.id.btnVolverMain_user);
        btnCarrito = findViewById(R.id.btnCarrito_user);

        lstProductos = findViewById(R.id.lstProductosUser);
        carrito = ((MyApplication) getApplication()).getCarrito();
        listar();


        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnCarrito.setOnClickListener(v -> {
            if (carrito != null && !carrito.getProductos().isEmpty()) {
                Intent intent = new Intent(UserProductosActivity.this, UserCarritoActivity.class);
                intent.putExtra("CARRITO", carrito);
                startActivity(intent);
            } else {
                Toast.makeText(UserProductosActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void listar() {
        API.getAllProducts(new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.content);
                    listaProductos = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Long id = jsonObject.getLong("id");
                        String nombre = jsonObject.getString("nombre");
                        double precio = jsonObject.getDouble("precio");
                        listaProductos.add(new Producto(id, nombre, precio));
                    }
                    productoUserAdapter = new ProductoUserAdapter(getApplicationContext(), R.layout.productoadd_user_cardview, listaProductos, carrito);
                    lstProductos.setAdapter(productoUserAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UtilREST.Response response) {
                Toast toast = Toast.makeText(UserProductosActivity.this, "Error al obtener los productos: " + response.exception, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_historialpedido, menu);
        getMenuInflater().inflate(R.menu.menu_logout, menu); // Añade el menú de logout
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_user_historial_pedido) {
            Intent intent = new Intent(this, UserHistorialPedidoActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) { // Añade el caso de logout
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Aquí puedes agregar el código para cerrar la sesión del usuario

        // Crea un nuevo intent para la actividad de login
        Intent intent = new Intent(this, Login.class);

        // Establece la bandera para limpiar la pila de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Inicia la actividad de login
        startActivity(intent);

        // Finaliza la actividad actual
        finish();
    }
}