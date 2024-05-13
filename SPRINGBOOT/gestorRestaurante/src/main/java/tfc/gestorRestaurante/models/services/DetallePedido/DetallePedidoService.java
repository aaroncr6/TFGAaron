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

@Service
public class DetallePedidoService implements IDetallePedidoService
{
    private final IDetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    @Autowired
    public DetallePedidoService(IDetallePedidoRepository detallePedidoRepository, DetallePedidoMapper detallePedidoMapper) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.detallePedidoMapper = detallePedidoMapper;
    }

    @Override
    @Transactional
    public List<DetallePedidoDTO> getAllDetallesPedido() {
        List<DetallePedido> detallesPedido = (List<DetallePedido>) detallePedidoRepository.findAll();
        return detallesPedido.stream()
                .map(detallePedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DetallePedidoDTO findDetallePedidoById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
        return detallePedidoMapper.toDTO(detallePedido);
    }

    @Override
    @Transactional
    public DetallePedidoDTO createDetallePedido(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = detallePedidoMapper.fromDTO(detallePedidoDTO);
        DetallePedido savedDetallePedido = detallePedidoRepository.save(detallePedido);
        return detallePedidoMapper.toDTO(savedDetallePedido);
    }

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

    @Override
    @Transactional
    public void deleteDetallePedido(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    @Override
@Transactional
public List<DetallePedido> getAllDetallesPedidoByPedidoId(Long pedidoId) {
    return detallePedidoRepository.findAllByPedidoId(pedidoId);
}

}
