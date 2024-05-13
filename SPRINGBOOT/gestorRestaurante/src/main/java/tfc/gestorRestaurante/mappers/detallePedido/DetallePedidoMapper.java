package tfc.gestorRestaurante.mappers.detallePedido;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class DetallePedidoMapper
{
    @Autowired
    private ModelMapper modelMapper;

    public DetallePedidoDTO toDTO(DetallePedido detallePedido) {
        DetallePedidoDTO detallePedidoDTO = modelMapper.map(detallePedido, DetallePedidoDTO.class);
        return detallePedidoDTO;
    }


    public DetallePedido fromDTO(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = modelMapper.map(detallePedidoDTO, DetallePedido.class);
        detallePedido.setCantidadDetallePedido(detallePedidoDTO.getCantidadDetalleProducto());
        detallePedido.setPrecioDetallePedido(detallePedidoDTO.getPrecioDetalleProducto());
        return detallePedido;
    }

    public List<DetallePedidoDTO> toDTO(List<DetallePedido> detallesPedido) {
        return detallesPedido.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
