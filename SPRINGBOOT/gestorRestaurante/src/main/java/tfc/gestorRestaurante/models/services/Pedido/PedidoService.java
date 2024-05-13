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

@Service
@Transactional
public class PedidoService implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    public PedidoService(IPedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    @Transactional
    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        return pedidoMapper.toDTOList(pedidos);
    }

    @Override
    @Transactional
    public PedidoDTO findPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }

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
                //DetallePedido detallePedido = detallePedidoMapper.fromDTO(detallePedidoDTO);
                detallePedidoService.createDetallePedido(detallePedidoDTO);
            }
        }



        return pedidoMapper.toDTO(savedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        existingPedido.setTotalPedido(pedidoDTO.getTotalPedido());
        existingPedido.setEstado(pedidoDTO.getEstado());

        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    @Transactional
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }




}