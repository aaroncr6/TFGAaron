package entity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

        // Cambia el color de fondo del CardView dependiendo del estado del pedido
        CardView cardView = (CardView) convertView;
        switch (pedido.getEstado()) {
            case "Aceptado":
                cardView.setCardBackgroundColor(Color.GREEN);
                break;
            case "Rechazado":
                cardView.setCardBackgroundColor(Color.RED);
                break;
            default:
                cardView.setCardBackgroundColor(Color.GRAY);
                break;
        }

        idPedido.setTypeface(null, Typeface.BOLD);
        estado.setTypeface(null, Typeface.BOLD);

        return convertView;
    }
}