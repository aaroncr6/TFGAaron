package tfc.gestorRestaurante.dto.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO
{
    private Long id;
    private double totalPedido;
    private Long userId;
    private List<DetallePedidoDTO> listaDetallesPedido;
    private String estado;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
