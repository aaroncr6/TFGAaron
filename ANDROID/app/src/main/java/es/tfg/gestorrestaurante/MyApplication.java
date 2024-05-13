package es.tfg.gestorrestaurante;

import android.app.Application;
import entity.Carrito;

public class MyApplication extends Application {
    private Carrito carrito;

    @Override
    public void onCreate() {
        super.onCreate();
        carrito = new Carrito();
    }

    public Carrito getCarrito() {
        return carrito;
    }
}