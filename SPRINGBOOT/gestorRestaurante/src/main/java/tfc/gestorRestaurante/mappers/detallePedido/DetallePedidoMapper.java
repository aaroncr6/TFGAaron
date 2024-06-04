package tfc.gestorRestaurante.mappers.detallePedido;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Mapper para la entidad DetallePedido y su correspondiente DTO.
 * Esta clase utiliza ModelMapper para mapear entre la entidad DetallePedido y el DTO.
 */
@Component
@NoArgsConstructor
public class DetallePedidoMapper
{
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Mapea de la entidad DetallePedido a DetallePedidoDTO.
     * @param detallePedido La entidad DetallePedido a ser mapeada.
     * @return El DetallePedidoDTO mapeado.
     */
    public DetallePedidoDTO toDTO(DetallePedido detallePedido) {
        DetallePedidoDTO detallePedidoDTO = modelMapper.map(detallePedido, DetallePedidoDTO.class);
        return detallePedidoDTO;
    }

    /**
     * Mapea de DetallePedidoDTO a la entidad DetallePedido.
     * También establece los detalles de cantidad y precio del DTO a la entidad.
     * @param detallePedidoDTO El DetallePedidoDTO a ser mapeado.
     * @return La entidad DetallePedido mapeada.
     */
    public DetallePedido fromDTO(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = modelMapper.map(detallePedidoDTO, DetallePedido.class);
        detallePedido.setCantidadDetallePedido(detallePedidoDTO.getCantidadDetalleProducto());
        detallePedido.setPrecioDetallePedido(detallePedidoDTO.getPrecioDetalleProducto());
        return detallePedido;
    }

    /**
     * Mapea una lista de entidades DetallePedido a una lista de DetallePedidoDTOs.
     * @param detallesPedido La lista de entidades DetallePedido a ser mapeada.
     * @return La lista de DetallePedidoDTOs mapeados.
     */
    public List<DetallePedidoDTO> toDTO(List<DetallePedido> detallesPedido) {
        return detallesPedido.stream().map(this::toDTO).collect(Collectors.toList());
    }
}