package es.tfg.gestorrestaurante;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.API;
import entity.Producto;
import entity.ProductoAdminAdapter;
import api.UtilREST;

public class AdminProductosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProductoAdminAdapter productoAdminAdapter;
    ListView lstProductos;
    List<Producto> listaProductos;
    List<Producto> listaProductosSinFiltrar;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_productos);

        lstProductos = findViewById(R.id.listAdminProductos);
        lstProductos.setOnItemClickListener(this);

        searchBar = findViewById(R.id.txtSearch_producto_admin);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarProductos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        registerForContextMenu(lstProductos);
        listar();

    }


    @Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    try {
        Producto producto = (Producto) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, AdminEditProduct.class);
        intent.putExtra("PRODUCT_ID", producto.getIdProducto());


        startActivityForResult(intent,1);
    } catch (Exception e) {
        Log.e("AdminProductosActivity", "Error en onItemClick: ", e);
    }
}

    @Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1) {
        if (resultCode == RESULT_OK) {
            listar();
        }
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_add_producto, menu);
        return true;
    }

   @Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.add_product:
            JSONObject newProduct = new JSONObject();
            try {
                newProduct.put("nombre", "Producto por defecto");
                newProduct.put("precio", 0.0);
                //newProduct.put("nombre_Categoria", "Por defecto");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            API.createProduct(newProduct, new UtilREST.OnResponseListener() {
                @Override
                public void onSuccess(UtilREST.Response response) {
                    Toast.makeText(AdminProductosActivity.this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                    listar();
                }

                @Override
                public void onError(UtilREST.Response response) {
                    Toast.makeText(AdminProductosActivity.this, "Error al crear el producto: " + response.exception, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}


    private void listar() {
        API.getAllProducts(new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.content);
                    listaProductos = new ArrayList<>();
                    listaProductosSinFiltrar = new ArrayList<>(); // Inicializa la lista de productos sin filtrar
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Long id = jsonObject.getLong("id");
                        String nombre = jsonObject.getString("nombre");
                        double precio = jsonObject.getDouble("precio");
                        Producto producto = new Producto(id, nombre, precio);
                        listaProductos.add(producto);
                        listaProductosSinFiltrar.add(producto); // Agrega el producto a la lista de productos sin filtrar
                    }
                    productoAdminAdapter = new ProductoAdminAdapter(getApplicationContext(), R.layout.producto_admin_cardview, listaProductos);
                    lstProductos.setAdapter(productoAdminAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UtilREST.Response response) {
                Toast toast = Toast.makeText(AdminProductosActivity.this, "Error al obtener los productos: " + response.exception, Toast.LENGTH_SHORT);
                toast.show();
            }
            });
    }

    private void filtrarProductos(String texto) {
        listaProductos.clear();
        if (texto.isEmpty()) {
            listaProductos.addAll(listaProductosSinFiltrar);
        } else {
            texto = texto.toLowerCase();
            for (Producto producto : listaProductosSinFiltrar) {
                if (producto.getNombreProducto().toLowerCase().contains(texto)) {
                    listaProductos.add(producto);
                }
            }
        }
        productoAdminAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_admin_eliminar_producto, menu);  // Infla el menú contextual
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Producto producto = (Producto) lstProductos.getItemAtPosition(info.position);

        if (item.getItemId() == R.id.eliminar_producto) {
            mostrarDialogo(producto);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void mostrarDialogo(Producto producto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar producto seleccionado");
        builder.setMessage("¿Estás seguro de eliminar el producto?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                API.deleteProductById(producto.getIdProducto(), new UtilREST.OnResponseListener() {
                    @Override
                    public void onSuccess(UtilREST.Response response) {
                        listaProductos.remove(producto);
                        productoAdminAdapter.notifyDataSetChanged();
                        Toast.makeText(AdminProductosActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                        listar();
                    }

                    @Override
                    public void onError(UtilREST.Response response) {
                        Toast.makeText(AdminProductosActivity.this, "Error al eliminar el producto: " + response.exception, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminProductosActivity.this, "Producto no eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}