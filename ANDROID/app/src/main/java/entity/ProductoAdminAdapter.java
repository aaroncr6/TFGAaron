package entity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import entity.Producto;
import es.tfg.gestorrestaurante.R;

public class ProductoAdminAdapter extends ArrayAdapter<Producto>
{

    private int mResource;

    Context context;

    private List<Producto> listaProductos;


    public ProductoAdminAdapter(@NonNull Context context, int resource, @NonNull List<Producto> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mResource = resource;
        this.listaProductos = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflador = LayoutInflater.from(this.getContext());
        View miFila = inflador.inflate(mResource, parent, false);

        TextView idProducto = miFila.findViewById(R.id.txtIdProductoCV);
        TextView nombreProducto = miFila.findViewById(R.id.txtNombreProductoCV);
        TextView precioProducto = miFila.findViewById(R.id.txtPrecioProductoCV);

        Long id = listaProductos.get(position).getIdProducto();
        idProducto.setText(id.toString());

        String nombre = listaProductos.get(position).getNombreProducto();
        nombreProducto.setText(nombre);

        double precio = listaProductos.get(position).getPrecioProducto();
        precioProducto.setText(String.valueOf(precio));

        return miFila;
    }
}
