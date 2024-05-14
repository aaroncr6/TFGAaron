package es.tfg.gestorrestaurante;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.API;
import api.UtilREST;
import entity.Pedido;
import entity.PedidoAdapter;

public class UserHistorialPedidoActivity extends AppCompatActivity {

    Long userId;
    private List<Pedido> listaPedidos;
    private PedidoAdapter pedidoAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_historial_pedido);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getLong("userId", 0);

        listView = findViewById(R.id.lstHistorialPedidos_userId);

        API.getPedidosByUserId(userId, new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.content);
                    int pendientes = 0;
                    int aceptados = 0;
                    int rechazados = 0;
                    listaPedidos = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Long id = jsonObject.getLong("id");
                        String estado = jsonObject.getString("estado");
                        Pedido pedido = new Pedido(id, estado);
                        listaPedidos.add(pedido);
                        if (estado.equals("Pendiente")) {
                            pendientes++;
                        } else if (estado.equals("Aceptado")) {
                            aceptados++;
                        } else if (estado.equals("Rechazado")) {
                            rechazados++;
                        }
                    }

                    TextView txtPendientes = findViewById(R.id.txt_user_history_pendientes);
                    TextView txtAceptados = findViewById(R.id.txt_user_history_aceptados);
                    TextView txtRechazados = findViewById(R.id.txt_user_history_rechazados);

                    txtPendientes.setText(String.valueOf(pendientes));
                    txtAceptados.setText(String.valueOf(aceptados));
                    txtRechazados.setText(String.valueOf(rechazados));

                    pedidoAdapter = new PedidoAdapter(getApplicationContext(), listaPedidos);
                    listView.setAdapter(pedidoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UtilREST.Response response) {
                Toast.makeText(UserHistorialPedidoActivity.this, "Error al obtener los pedidos: " + response.exception, Toast.LENGTH_SHORT).show();
            }
        });
    }

}