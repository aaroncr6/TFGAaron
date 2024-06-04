package tfc.gestorRestaurante.models.services.Pedido;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.mappers.Pedido.PedidoMapper;
import tfc.gestorRestaurante.mappers.detallePedido.DetallePedidoMapper;
import tfc.gestorRestaurante.models.entity.DetallePedido;
import tfc.gestorRestaurante.models.entity.Pedido;
import tfc.gestorRestaurante.models.repository.IPedidoRepository;
import tfc.gestorRestaurante.models.services.DetallePedido.DetallePedidoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de servicio que implementa la interfaz IPedidoService.
 * Proporciona métodos para manipular y obtener información de los pedidos.
 */
@Service
@Transactional
public class PedidoService implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @Autowired
    private DetallePedidoService detallePedidoService;

    /**
     * Constructor de la clase PedidoService.
     * @param pedidoRepository Repositorio para acceder a los datos de los pedidos.
     * @param pedidoMapper Mapper para convertir entre Pedido y PedidoDTO.
     */
    @Autowired
    public PedidoService(IPedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    /**
     * Obtiene todos los pedidos.
     * @return Una lista de PedidoDTO.
     */
    @Override
    @Transactional
    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        return pedidoMapper.toDTOList(pedidos);
    }

    /**
     * Encuentra un pedido por su ID.
     * @param id El ID del pedido a encontrar.
     * @return Un PedidoDTO del pedido encontrado.
     * @throws RuntimeException si no se encuentra el pedido.
     */
    @Override
    @Transactional
    public PedidoDTO findPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }

    /**
     * Crea un nuevo pedido.
     * @param pedidoDTO El DTO del pedido a crear.
     * @return Un PedidoDTO del pedido creado.
     */
    @Override
    @Transactional
    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.fromDTO(pedidoDTO);
        pedido.setFechaInicio(LocalDateTime.now());
        Pedido savedPedido = pedidoRepository.save(pedido);
        List<DetallePedidoDTO> detallesPedidoDTO = pedidoDTO.getListaDetallesPedido();

        if (detallesPedidoDTO != null) {
            for (DetallePedidoDTO detallePedidoDTO : detallesPedidoDTO) {
                detallePedidoDTO.setIdPedido(savedPedido.getId());
                detallePedidoService.createDetallePedido(detallePedidoDTO);
            }
        }

        return pedidoMapper.toDTO(savedPedido);
    }

    /**
     * Actualiza un pedido existente.
     * @param id El ID del pedido a actualizar.
     * @param pedidoDTO El DTO del pedido con los datos actualizados.
     * @return Un PedidoDTO del pedido actualizado.
     * @throws RuntimeException si no se encuentra el pedido.
     */
    @Override
    @Transactional
    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (pedidoDTO.getTotalPedido() != 0) {
            existingPedido.setTotalPedido(pedidoDTO.getTotalPedido());
        }
        existingPedido.setEstado(pedidoDTO.getEstado());
        if (pedidoDTO.getFechaFin() != null) {
            existingPedido.setFechaFin(pedidoDTO.getFechaFin());
        }

        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    /**
     * Elimina un pedido.
     * @param id El ID del pedido a eliminar.
     */
    @Override
    @Transactional
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    /**
     * Obtiene todos los pedidos por el ID de un usuario.
     * @param userId El ID del usuario.
     * @return Una lista de PedidoDTO que representa todos los pedidos para el usuario dado.
     */
    @Override
    public List<PedidoDTO> findPedidosByUserId(Long userId) {
        List<Pedido> pedidos = pedidoRepository.findPedidosByUserId(userId);
        return pedidos.stream().map(pedidoMapper::toDTO).collect(Collectors.toList());
    }
}