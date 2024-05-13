package entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.tfg.gestorrestaurante.R;

public class PedidoAdapter extends ArrayAdapter<Pedido> {

    public PedidoAdapter(Context context, List<Pedido> pedidos) {
        super(context, R.layout.pedidos_admin_cardview, pedidos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pedidos_admin_cardview, parent, false);
        }

        Pedido pedido = getItem(position);

        TextView idPedido = convertView.findViewById(R.id.txtIdPedido_pedido_adminCV);
        TextView estado = convertView.findViewById(R.id.txtEstado_pedido_adminCV);

        // Verificar si el ID del pedido es null o 0
        if (pedido.getIdPedido() == null || pedido.getIdPedido() == 0) {
            idPedido.setText("ID no disponible");
        } else {
            idPedido.setText(String.valueOf(pedido.getIdPedido()));
        }

        estado.setText(pedido.getEstado());

        return convertView;
    }

}