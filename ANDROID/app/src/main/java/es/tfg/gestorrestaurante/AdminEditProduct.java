package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import api.API;
import api.UtilREST;

public class AdminEditProduct extends AppCompatActivity {

    TextView idEditText,nombreEditText, precioEditText, descripcionEditText;
    Button btnGuardar, btnCancelar;
    long productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);

        idEditText = findViewById(R.id.id_editProducto_admin);
        nombreEditText = findViewById(R.id.nombre_editProducto_admin);
        precioEditText = findViewById(R.id.precio_editProducto_admin);
        descripcionEditText = findViewById(R.id.descripcion_editProducto_admin);
        btnGuardar = findViewById(R.id.btnGuardar_editProducto_admin);
        btnCancelar = findViewById(R.id.btnCancelar_editProducto_admin);

        productId = getIntent().getLongExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            // Cargar los detalles del producto desde la base de datos
            API.getProductById(productId, new UtilREST.OnResponseListener() {
                @Override
                public void onSuccess(UtilREST.Response response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.content);
                        Long id = jsonObject.getLong("id");
                        String nombre = jsonObject.getString("nombre");
                        double precio = jsonObject.getDouble("precio");
                        String descripcion = jsonObject.getString("descripcion");

                        idEditText.setText(String.valueOf(id));
                        nombreEditText.setText(nombre);
                        precioEditText.setText(String.valueOf(precio));
                        descripcionEditText.setText(descripcion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UtilREST.Response response) {
                    showToast("Error al cargar el producto: " + response.exception);
                }
            });
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                double precio = Double.parseDouble(precioEditText.getText().toString());
                String descripcion = descripcionEditText.getText().toString();

                JSONObject updatedProduct = new JSONObject();
                try {
                    updatedProduct.put("nombre", nombre);
                    updatedProduct.put("precio", precio);
                    updatedProduct.put("descripcion", descripcion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                API.updateProductById(productId, updatedProduct, new UtilREST.OnResponseListener() {
                    @Override
                    public void onSuccess(UtilREST.Response response) {
                        showToast("Producto actualizado con éxito");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onError(UtilREST.Response response) {
                        showToast("Error al actualizar el producto: " + response.exception);
                    }
                });
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showToast(String message) {
        // Crea el layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Infla el layout desde xml
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Obtiene el TextView y establece el mensaje
        TextView toastText = layout.findViewById(R.id.toastMessage);
        toastText.setText(message);

        // Crea el nuevo toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        // Muestra el toast
        toast.show();
    }
}