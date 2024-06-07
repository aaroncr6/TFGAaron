package es.tfg.gestorrestaurante;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

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

    private static final int NOTIFICATION_PERMISSION_CODE = 200;

    private CarritoAdapter carritoAdapter;
    ListView lstProductos;
    List<DetallePedido> listaProductos;
    Carrito carrito;
    Button btnVolver, btnRealizarPedido;

    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_carrito);

        lstProductos = findViewById(R.id.lstCarrito_CarritoUser);
        btnVolver = findViewById(R.id.btnVolverCarrito_CarritoUser);
        btnRealizarPedido = findViewById(R.id.btnMakePedido_CarritoUser);
        carrito = ((MyApplication) getApplication()).getCarrito();

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getLong("userId", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PedidoChannel";
            String description = "Canal para notificación de pedidos";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PedidoChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        if (carrito == null) {
            Toast.makeText(this, "El carrito está vacio", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        listaProductos = carrito.getProductos();

        carritoAdapter = new CarritoAdapter(getApplicationContext(), R.layout.detalleproducto_carrito_cardview, listaProductos, carrito);
        lstProductos.setAdapter(carritoAdapter);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (carrito.getProductos().isEmpty()) {
                    Toast.makeText(UserCarritoActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ContextCompat.checkSelfPermission(UserCarritoActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserCarritoActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
                } else {
                    realizarPedido();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                realizarPedido();
            } else {
                Toast.makeText(this, "Permiso de notificación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void realizarPedido() {
        final Double[] totalpedido = {0.0};
        JSONObject pedido = new JSONObject();
        try {
            // Convertir la lista de productos del carrito a un JSONArray
            JSONArray productosArray = new JSONArray();
            for (DetallePedido detallePedido : carrito.getProductos()) {
                totalpedido[0] = totalpedido[0] + (detallePedido.getProducto().getPrecioProducto() * detallePedido.getCantidad());
                Producto producto = detallePedido.getProducto();
                JSONObject productoJson = new JSONObject();
                productoJson.put("idProducto", producto.getIdProducto());
                productoJson.put("cantidadDetalleProducto", detallePedido.getCantidad());
                productoJson.put("precioDetalleProducto", producto.getPrecioProducto());
                productoJson.put("idPedido", 0);
                productosArray.put(productoJson);
            }

            pedido.put("totalPedido", totalpedido[0]);
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
                    long pedidoId = jsonResponse.getLong("id");

                    // Crear la notificación
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(UserCarritoActivity.this, "PedidoChannel")
                            .setSmallIcon(R.drawable.ic_launcher_foreground) // reemplaza esto con tu propio icono
                            .setContentTitle("Pedido realizado con éxito")
                            .setContentText("Total pagado: " + totalpedido[0])
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    // Mostrar la notificación
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserCarritoActivity.this);
                    if (ContextCompat.checkSelfPermission(UserCarritoActivity.this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        notificationManager.notify(0, builder.build());
                    }
                    carrito.vaciarCarrito();
                    carritoAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(UserCarritoActivity.this, UserProductosActivity.class);
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
}