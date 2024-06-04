package tfc.gestorRestaurante.models.services.DetallePedido;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.mappers.detallePedido.DetallePedidoMapper;
import tfc.gestorRestaurante.models.entity.DetallePedido;
import tfc.gestorRestaurante.models.repository.IDetallePedidoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de servicio que implementa la interfaz IDetallePedidoService.
 * Proporciona métodos para manipular y obtener información de los detalles de los pedidos.
 */
@Service
public class DetallePedidoService implements IDetallePedidoService {
    private final IDetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    /**
     * Constructor de la clase DetallePedidoService.
     * @param detallePedidoRepository Repositorio para acceder a los datos de los detalles de los pedidos.
     * @param detallePedidoMapper Mapper para convertir entre DetallePedido y DetallePedidoDTO.
     */
    @Autowired
    public DetallePedidoService(IDetallePedidoRepository detallePedidoRepository, DetallePedidoMapper detallePedidoMapper) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.detallePedidoMapper = detallePedidoMapper;
    }

    /**
     * Obtiene todos los detalles de los pedidos.
     * @return Una lista de DetallePedidoDTO.
     */
    @Override
    @Transactional
    public List<DetallePedidoDTO> getAllDetallesPedido() {
        List<DetallePedido> detallesPedido = (List<DetallePedido>) detallePedidoRepository.findAll();
        return detallesPedido.stream()
                .map(detallePedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Encuentra un detalle de pedido por su ID.
     * @param id El ID del detalle de pedido a encontrar.
     * @return Un DetallePedidoDTO del detalle de pedido encontrado.
     * @throws RuntimeException si no se encuentra el detalle de pedido.
     */
    @Override
    @Transactional
    public DetallePedidoDTO findDetallePedidoById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
        return detallePedidoMapper.toDTO(detallePedido);
    }

    /**
     * Crea un nuevo detalle de pedido.
     * @param detallePedidoDTO El DTO del detalle de pedido a crear.
     * @return Un DetallePedidoDTO del detalle de pedido creado.
     */
    @Override
    @Transactional
    public DetallePedidoDTO createDetallePedido(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = detallePedidoMapper.fromDTO(detallePedidoDTO);
        DetallePedido savedDetallePedido = detallePedidoRepository.save(detallePedido);
        return detallePedidoMapper.toDTO(savedDetallePedido);
    }

    /**
     * Actualiza un detalle de pedido existente.
     * @param id El ID del detalle de pedido a actualizar.
     * @param detallePedidoDTO El DTO del detalle de pedido con los datos actualizados.
     * @return Un DetallePedidoDTO del detalle de pedido actualizado.
     * @throws RuntimeException si no se encuentra el detalle de pedido.
     */
    @Override
    @Transactional
    public DetallePedidoDTO updateDetallePedido(Long id, DetallePedidoDTO detallePedidoDTO) {
        DetallePedido existingDetallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));

        existingDetallePedido.setCantidadDetallePedido(detallePedidoDTO.getCantidadDetallePedido());
        existingDetallePedido.setPrecioDetallePedido(detallePedidoDTO.getPrecioDetallePedido());
        // Puedes actualizar más campos según sea necesario

        DetallePedido updatedDetallePedido = detallePedidoRepository.save(existingDetallePedido);
        return detallePedidoMapper.toDTO(updatedDetallePedido);
    }

    /**
     * Elimina un detalle de pedido.
     * @param id El ID del detalle de pedido a eliminar.
     */
    @Override
    @Transactional
    public void deleteDetallePedido(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    /**
     * Obtiene todos los detalles de los pedidos por el ID de un pedido.
     * @param pedidoId El ID del pedido.
     * @return Una lista de DetallePedido.
     */
    @Override
    @Transactional
    public List<DetallePedido> getAllDetallesPedidoByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findAllByPedidoId(pedidoId);
    }
}