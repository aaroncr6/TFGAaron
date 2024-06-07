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

/**
 * Clase de prueba para PedidoService.
 * Esta clase utiliza la extensión Mockito para simular las dependencias.
 */
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
     * Esta prueba verifica que el método findPedidoById devuelve el PedidoDTO esperado para un id dado.
     * Se crea un Pedido y PedidoDTO de prueba con el mismo id, y se simula la respuesta del repositorio y del mapeador.
     * Luego se compara el resultado del método con el id esperado.
     */
    @Test
    public void testFindPedidoById() {
        Long id = 32L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(id);

        // Simulamos la respuesta del repositorio y del mapeador
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        // Llamamos al método que estamos probando
        PedidoDTO result = pedidoService.findPedidoById(id);

        // Verificamos el resultado
        assertEquals(id, result.getId());
    }
}