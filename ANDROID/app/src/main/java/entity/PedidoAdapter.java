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

/**
 * Clase PedidoAdapter que extiende de ArrayAdapter<Pedido> y se encarga de adaptar los datos de los
 * pedidos a un CardView para mostrarlos en la interfaz de usuario.
 */
public class PedidoAdapter extends ArrayAdapter<Pedido> {

    /**
     * Constructor de la clase PedidoAdapter.
     * @param context Contexto de la aplicación.
     * @param pedidos Lista de pedidos a adaptar.
     */
    public PedidoAdapter(Context context, List<Pedido> pedidos) {
        super(context, R.layout.pedidos_admin_cardview, pedidos);
    }

    /**
     * Método getView que se encarga de adaptar los datos de los pedidos a un CardView.
     * @param position Posición del pedido en la lista.
     * @param convertView Vista del CardView.
     * @param parent Grupo de vistas padre.
     * @return Vista del CardView con los datos del pedido.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pedidos_admin_cardview, parent, false);
        }

        Pedido pedido = getItem(position);

        TextView idPedido = convertView.findViewById(R.id.txtIdPedido_pedido_adminCV);
        TextView estado = convertView.findViewById(R.id.txtEstado_pedido_adminCV);

        // Mostrar el ID del pedido si está disponible
        if (pedido.getIdPedido() == null || pedido.getIdPedido() == 0) {
            idPedido.setText("ID no disponible");
        } else {
            idPedido.setText(String.valueOf(pedido.getIdPedido()));
        }

        estado.setText(pedido.getEstado());

        // Cambiar el color del CardView en función del estado del pedido
        CardView cardView = (CardView) convertView;
        switch (pedido.getEstado()) {
            case "Aceptado":
                cardView.setCardBackgroundColor(Color.argb(255, 144, 238, 144));
                break;
            case "Rechazado":
                cardView.setCardBackgroundColor(Color.argb(255, 255, 192, 192));
                break;
            default:
                cardView.setCardBackgroundColor(Color.argb(255, 220, 220, 220));
                break;
        }

        // Establecer negrita en los textos
        idPedido.setTypeface(null, Typeface.BOLD);
        estado.setTypeface(null, Typeface.BOLD);

        return convertView;
    }
}