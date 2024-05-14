package entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.tfg.gestorrestaurante.R;

public class CarritoAdapter extends ArrayAdapter<DetallePedido> {

    private int mResource;
    private List<DetallePedido> listaDetallePedidos;
    private Carrito carrito;

    public CarritoAdapter(Context context, int resource, List<DetallePedido> objects, Carrito carrito) {
        super(context, resource, objects);
        this.mResource = resource;
        this.listaDetallePedidos = objects;
        this.carrito = carrito;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflador = LayoutInflater.from(this.getContext());
        View miFila = inflador.inflate(mResource, parent, false);

        TextView idProducto = miFila.findViewById(R.id.idproducto_detallepedidoCV);
        TextView nombreProducto = miFila.findViewById(R.id.name_detallepedidoCV);
        TextView precioProducto = miFila.findViewById(R.id.totalprice_detallepedidoCV);
        TextView totalProductos = miFila.findViewById(R.id.totalProduc_detallePEdidoCV);
        Button btnEliminarProducto = miFila.findViewById(R.id.btnDeleteProductosCarrito);

        DetallePedido detallePedido = listaDetallePedidos.get(position);
        Producto producto = detallePedido.getProducto();

        idProducto.setText(String.valueOf(producto.getIdProducto()));
        nombreProducto.setText(producto.getNombreProducto());
        precioProducto.setText(String.valueOf(producto.getPrecioProducto() * detallePedido.getCantidad()));
        totalProductos.setText(String.valueOf(detallePedido.getCantidad()));

        // Set the tag for the button to the DetallePedido object
        btnEliminarProducto.setTag(detallePedido);

        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetallePedido detallePedido = (DetallePedido) v.getTag();

                if (detallePedido.getCantidad() > 1) {
                    detallePedido.setCantidad(detallePedido.getCantidad() - 1);
                } else {
                    carrito.eliminarProducto(detallePedido);
                }

                notifyDataSetChanged();
            }
        });

        return miFila;
    }
}