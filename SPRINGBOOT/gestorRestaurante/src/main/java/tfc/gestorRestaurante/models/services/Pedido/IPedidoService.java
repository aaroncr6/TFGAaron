package tfc.gestorRestaurante.models.services.Pedido;

import jakarta.transaction.Transactional;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.models.entity.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos de la clase PedidoService.
 * Esta interfaz proporciona una abstracción para el servicio de pedidos.
 */
public interface IPedidoService
{
    /**
     * Obtiene todos los pedidos.
     * @return Una lista de PedidoDTO que representa todos los pedidos.
     */
    @Transactional
    List<PedidoDTO> getAllPedidos();

    /**
     * Encuentra un pedido por su ID.
     * @param id El ID del pedido a encontrar.
     * @return Un PedidoDTO que representa el pedido encontrado.
     */
    @Transactional
    PedidoDTO findPedidoById(Long id);

    /**
     * Crea un nuevo pedido.
     * @param pedidoDTO Un PedidoDTO que representa el pedido a crear.
     * @return Un PedidoDTO que representa el pedido creado.
     */
    @Transactional
    PedidoDTO createPedido(PedidoDTO pedidoDTO);

    /**
     * Actualiza un pedido existente.
     * @param id El ID del pedido a actualizar.
     * @param pedidoDTO Un PedidoDTO que representa el pedido con los datos actualizados.
     * @return Un PedidoDTO que representa el pedido actualizado.
     */
    @Transactional
    PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO);

    /**
     * Elimina un pedido.
     * @param id El ID del pedido a eliminar.
     */
    @Transactional
    void deletePedido(Long id);

    /**
     * Obtiene todos los pedidos por el ID de un usuario.
     * @param userId El ID del usuario.
     * @return Una lista de PedidoDTO que representa todos los pedidos para el usuario dado.
     */
    @Transactional
    List<PedidoDTO> findPedidosByUserId(Long userId);
}