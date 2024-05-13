package tfc.gestorRestaurante.mappers.detallePedido;

import jakarta.transaction.Transactional;
import org.modelmapper.AbstractConverter;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;
import java.util.stream.Collectors;

public class DetallePedidoListConverter extends AbstractConverter<List<DetallePedido>, List<Long>>
{



    @Override
    protected List<Long> convert(List<DetallePedido> detallePedidos) {
        return detallePedidos.stream()
                .map(DetallePedido::getIdDetallePedido)
                .collect(Collectors.toList());
    }
}
