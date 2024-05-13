package entity;

import java.io.Serializable;

public class Producto implements Serializable
{
    private Long id;
    private String nombre;
    private String ingredientes;
    private String descripcion;
    private double precio;
    private String image;
    private String nombreCategoria;

    public Producto(Long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
//        this.ingredientes = ingredientes;
//        this.descripcion = descripcion;
        this.precio = precio;
//        this.image = image;
//       this.nombreCategoria = nombreCategoria;
    }

    public Producto(Long id, String nombre, double precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
//        this.ingredientes = ingredientes;
        this.descripcion = descripcion;
        this.precio = precio;
//        this.image = image;
//        this.nombreCategoria = nombreCategoria;
    }



    public Long getIdProducto() {
        return id;
    }

    public void setIdProducto(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombre;
    }

    public void setNombreProducto(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioProducto() {
        return precio;
    }

    public void setPrecioProducto(double precio) {
        this.precio = precio;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
