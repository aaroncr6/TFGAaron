package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {

    private Long userId;
    private List<DetallePedido> productos;


    public Carrito() {
        productos = new ArrayList<>();
    }

    public Carrito(Long userId) {
        this.userId = userId;
        productos = new ArrayList<>();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(new DetallePedido(producto, cantidad));
    }

    public void eliminarProducto(DetallePedido detallePedido) {
        productos.remove(detallePedido);
    }

    //Method to empty the order
    public void vaciarCarrito() {
        this.productos.clear();
    }

    public double getTotal() {
        double total = 0;
        for (DetallePedido detallePedido : productos) {
            total += detallePedido.getProducto().getPrecioProducto() * detallePedido.getCantidad();
        }
        return total;
    }

    public List<DetallePedido> getProductos() {
        return productos;
    }
}
