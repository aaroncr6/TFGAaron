package tfc.gestorRestaurante.models.services.Pedido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.mappers.Pedido.PedidoMapper;
import tfc.gestorRestaurante.models.entity.Pedido;
import tfc.gestorRestaurante.models.repository.IPedidoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private IPedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    /**
     * Prueba del método findPedidoById, de la clase PedidoService.
     */
    @Test
    public void testFindPedidoById() {
        Long id = 32L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(id);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = pedidoService.findPedidoById(id);

        assertEquals(id, result.getId());
    }
}