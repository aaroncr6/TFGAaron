package tfc.gestorRestaurante.models.services.Pedido;

import jakarta.transaction.Transactional;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.models.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoService
{

    @Transactional
    List<PedidoDTO> getAllPedidos();

    @Transactional
    PedidoDTO findPedidoById(Long id);

    @Transactional
    PedidoDTO createPedido(PedidoDTO pedidoDTO);

    @Transactional
    PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO);

    @Transactional
    void deletePedido(Long id);
}
