package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pedido implements Serializable
{
    private Long idPedido;
    private String estado;
    private LocalDateTime fechaFin;

    private double totalPedido;

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

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
}
