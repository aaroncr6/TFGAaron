package entity;

import java.io.Serializable;

public class Pedido implements Serializable
{
    private Long idPedido;
    private String estado;


    public Pedido(Long idPedido, String estado) {
        this.idPedido = idPedido;
        this.estado = estado;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
