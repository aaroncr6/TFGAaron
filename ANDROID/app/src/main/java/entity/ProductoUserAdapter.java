package entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import entity.Carrito;
import entity.DetallePedido;
import entity.Producto;
import es.tfg.gestorrestaurante.R;

public class ProductoUserAdapter extends ArrayAdapter<Producto> {

    private int mResource;
    private List<Producto> listaProductos;
    private Carrito carrito;

    public ProductoUserAdapter(Context context, int resource, List<Producto> objects, Carrito carrito) {
        super(context, resource, objects);
        this.mResource = resource;
        this.listaProductos = objects;
        this.carrito = carrito;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflador = LayoutInflater.from(this.getContext());
        View miFila = inflador.inflate(mResource, parent, false);

        TextView idProducto = miFila.findViewById(R.id.id_addproduct_user);
        TextView nombreProducto = miFila.findViewById(R.id.name_addproduct_user);
        TextView precioProducto = miFila.findViewById(R.id.price_addproduct_user);
        Button btnAddProduct = miFila.findViewById(R.id.btnAddProduct_user);

        Producto producto = listaProductos.get(position);

        idProducto.setText(String.valueOf(producto.getIdProducto()));
        nombreProducto.setText(producto.getNombreProducto());
        precioProducto.setText(String.valueOf(producto.getPrecioProducto()));

        // Aquí puedes agregar un OnClickListener al botón para agregar el producto al carrito
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Producto añadido al carrito", Toast.LENGTH_SHORT).show();

                // Verificar si el producto ya está en el carrito
                DetallePedido detalleExistente = null;
                for (DetallePedido detalle : carrito.getProductos()) {
                    if (detalle.getProducto().getIdProducto().equals(producto.getIdProducto())) {
                        detalleExistente = detalle;
                        break;
                    }
                }

                // Si el producto ya está en el carrito, incrementar la cantidad
                if (detalleExistente != null) {
                    detalleExistente.setCantidad(detalleExistente.getCantidad() + 1);
                } else {
                    // Si no existe, agregar el producto al carrito con cantidad 1
                    carrito.agregarProducto(producto, 1);
                }
            }
        });

        return miFila;
    }
}
