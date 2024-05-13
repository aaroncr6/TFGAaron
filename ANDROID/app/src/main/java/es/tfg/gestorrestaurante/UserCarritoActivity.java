package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import api.API;
import entity.Carrito;
import entity.DetallePedido;
import entity.Producto;
import entity.CarritoAdapter;
import api.UtilREST;

public class UserCarritoActivity extends AppCompatActivity {

    private CarritoAdapter carritoAdapter;
    ListView lstProductos;
    List<DetallePedido> listaProductos;
    Carrito carrito;
    Button btnBorrar, btnRealizarPedido;

    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_carrito);

        lstProductos = findViewById(R.id.lstCarrito_CarritoUser);
        btnBorrar = findViewById(R.id.btnBorrarCarrito_CarritoUser);
        btnRealizarPedido = findViewById(R.id.btnMakePedido_CarritoUser);
        carrito = ((MyApplication) getApplication()).getCarrito();

        userId = getIntent().getLongExtra("USER_ID", 0);
        ((MyApplication) getApplication()).getCarrito().setUserId(userId);

        if(carrito == null){
            Toast.makeText(this, "El carrito está vacio", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        listaProductos = carrito.getProductos();

        carritoAdapter = new CarritoAdapter(getApplicationContext(), R.layout.detalleproducto_carrito_cardview, listaProductos, carrito);
        lstProductos.setAdapter(carritoAdapter);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DetallePedido detallePedido : listaProductos) {
                    carrito.eliminarProducto(detallePedido);
                }
                carritoAdapter.notifyDataSetChanged();
                Toast.makeText(UserCarritoActivity.this, "Todos los productos han sido borrados del carrito", Toast.LENGTH_SHORT).show();
            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(carrito.getProductos().isEmpty()){
                    Toast.makeText(UserCarritoActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double totalpedido = 0.0;
                JSONObject pedido = new JSONObject();
                try {
                    // Convertir la lista de productos del carrito a un JSONArray
                    JSONArray productosArray = new JSONArray();
                    for (DetallePedido detallePedido : carrito.getProductos()) {
                        totalpedido = totalpedido + (detallePedido.getProducto().getPrecioProducto() * detallePedido.getCantidad());
                        Producto producto = detallePedido.getProducto();
                        JSONObject productoJson = new JSONObject();
                        productoJson.put("idProducto", producto.getIdProducto());
                        productoJson.put("cantidadDetalleProducto", detallePedido.getCantidad());
                        productoJson.put("precioDetalleProducto", producto.getPrecioProducto());
                        productoJson.put("idPedido", 0);
                        productosArray.put(productoJson);
                    }

                    pedido.put("totalPedido", totalpedido);
                    pedido.put("userId", userId);
                    pedido.put("listaDetallesPedido", productosArray);
                    pedido.put("estado", "Pendiente"); // Añade el estado por defecto
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                API.createPedido(pedido, new UtilREST.OnResponseListener() {
                    @Override
                    public void onSuccess(UtilREST.Response response) {
                        try {
                            // Convertir la respuesta a JSONObject
                            JSONObject jsonResponse = new JSONObject(response.content);

                            // Obtener el ID del pedido creado
                            long pedidoId = jsonResponse.getLong("id");

                            Toast.makeText(UserCarritoActivity.this, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                            carritoAdapter.notifyDataSetChanged();

                            Intent intent = new Intent(UserCarritoActivity.this, UserMainActivity.class);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UtilREST.Response response) {
                        Toast.makeText(UserCarritoActivity.this, "Error al realizar el pedido: " + response.exception, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}