package tfc.gestorRestaurante.models.services.DetallePedido;

import jakarta.transaction.Transactional;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;

/**
 * Interfaz que define los métodos de la clase DetallePedidoService.
 * Esta interfaz proporciona una abstracción para el servicio de detalles de pedidos.
 */
public interface IDetallePedidoService
{
    /**
     * Obtiene todos los detalles de los pedidos.
     * @return Una lista de DetallePedidoDTO que representa todos los detalles de los pedidos.
     */
    @Transactional
    List<DetallePedidoDTO> getAllDetallesPedido();

    /**
     * Encuentra un detalle de pedido por su ID.
     * @param id El ID del detalle de pedido a encontrar.
     * @return Un DetallePedidoDTO que representa el detalle de pedido encontrado.
     */
    @Transactional
    DetallePedidoDTO findDetallePedidoById(Long id);

    /**
     * Crea un nuevo detalle de pedido.
     * @param detallePedidoDTO Un DetallePedidoDTO que representa el detalle de pedido a crear.
     * @return Un DetallePedidoDTO que representa el detalle de pedido creado.
     */
    @Transactional
    DetallePedidoDTO createDetallePedido(DetallePedidoDTO detallePedidoDTO);

    /**
     * Actualiza un detalle de pedido existente.
     * @param id El ID del detalle de pedido a actualizar.
     * @param detallePedidoDTO Un DetallePedidoDTO que representa el detalle de pedido con los datos actualizados.
     * @return Un DetallePedidoDTO que representa el detalle de pedido actualizado.
     */
    @Transactional
    DetallePedidoDTO updateDetallePedido(Long id, DetallePedidoDTO detallePedidoDTO);

    /**
     * Elimina un detalle de pedido.
     * @param id El ID del detalle de pedido a eliminar.
     */
    @Transactional
    void deleteDetallePedido(Long id);

    /**
     * Obtiene todos los detalles de los pedidos por el ID de un pedido.
     * @param pedidoId El ID del pedido.
     * @return Una lista de DetallePedido que representa todos los detalles de los pedidos para el pedido dado.
     */
    List<DetallePedido> getAllDetallesPedidoByPedidoId(Long pedidoId);
}