package es.tfg.gestorrestaurante;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.API;
import api.UtilREST;
import entity.Pedido;
import entity.PedidoAdapter;

public class AdminPedidosActivity extends AppCompatActivity {

    private List<Pedido> listaPedidos;
    private List<Pedido> listaPedidosSinFiltrar;
    private PedidoAdapter pedidoAdapter;
    private ListView listView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pedidos);

        listView = findViewById(R.id.lstPedidos_adminCV);
        searchBar = findViewById(R.id.searchBar_adminPedidos);
        registerForContextMenu(listView); // Registra la vista para el menú contextual

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarPedidos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listarPedidos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin_update_pedido, menu); // Asume que tienes un archivo XML de menú llamado menu_admin_update_pedido
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.admin_pedido_accept:
                updatePedidoState(info.position, "Aceptado");
                return true;
            case R.id.admin_pedido_reject:
                updatePedidoState(info.position, "Rechazado");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updatePedidoState(int position, String newState) {
        Pedido pedido = listaPedidos.get(position);
        pedido.setEstado(newState);
        JSONObject pedidoJson = new JSONObject();
        try {
            pedidoJson.put("id", pedido.getIdPedido());
            pedidoJson.put("estado", newState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.updatePedidoById(pedido.getIdPedido(), pedidoJson, new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                listarPedidos();
            }

            @Override
            public void onError(UtilREST.Response response) {
                Toast.makeText(AdminPedidosActivity.this, "Error al actualizar el estado del pedido: " + response.exception, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarPedidos() {
        API.getAllPedidos(new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.content);
                    listaPedidos = new ArrayList<>();
                    listaPedidosSinFiltrar = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Long id = jsonObject.getLong("id");
                        String estado = jsonObject.getString("estado");
                        Pedido pedido = new Pedido(id, estado);
                        listaPedidos.add(pedido);
                        listaPedidosSinFiltrar.add(pedido);
                    }
                    pedidoAdapter = new PedidoAdapter(getApplicationContext(), listaPedidos);
                    listView.setAdapter(pedidoAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UtilREST.Response response) {
                Toast toast = Toast.makeText(AdminPedidosActivity.this, "Error al obtener los pedidos: " + response.exception, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void filtrarPedidos(String texto) {
        listaPedidos.clear();
        if (texto.isEmpty()) {
            listaPedidos.addAll(listaPedidosSinFiltrar);
        } else {
            texto = texto.toLowerCase();
            for (Pedido pedido : listaPedidosSinFiltrar) {
                if (pedido.getEstado().toLowerCase().contains(texto)) {
                    listaPedidos.add(pedido);
                }
            }
        }
        pedidoAdapter.notifyDataSetChanged();
    }
}