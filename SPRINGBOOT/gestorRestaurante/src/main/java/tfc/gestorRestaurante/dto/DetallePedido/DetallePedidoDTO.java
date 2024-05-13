package tfc.gestorRestaurante.dto.DetallePedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO
{
    private Long idDetallePedido;
    private Long idProducto;
    private int cantidadDetallePedido;
    private double precioDetallePedido;
    private Long idPedido;


    public int getCantidadDetalleProducto() {
        return cantidadDetallePedido;
    }

    public void setCantidadDetalleProducto(int cantidadDetalleProducto) {
        this.cantidadDetallePedido = cantidadDetalleProducto;
    }

    public double getPrecioDetalleProducto() {
        return precioDetallePedido;
    }

    public void setPrecioDetalleProducto(double precioDetalleProducto) {
        this.precioDetallePedido = precioDetalleProducto;
    }
}
