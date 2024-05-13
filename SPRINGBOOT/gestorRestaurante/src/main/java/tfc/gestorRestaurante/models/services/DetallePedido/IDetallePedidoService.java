package tfc.gestorRestaurante.models.services.DetallePedido;

import jakarta.transaction.Transactional;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;

public interface IDetallePedidoService
{

    @Transactional
    List<DetallePedidoDTO> getAllDetallesPedido();

    @Transactional
    DetallePedidoDTO findDetallePedidoById(Long id);

    @Transactional
    DetallePedidoDTO createDetallePedido(DetallePedidoDTO detallePedidoDTO);

    @Transactional
    DetallePedidoDTO updateDetallePedido(Long id, DetallePedidoDTO detallePedidoDTO);

    @Transactional
    void deleteDetallePedido(Long id);

    List<DetallePedido> getAllDetallesPedidoByPedidoId(Long pedidoId);
}
