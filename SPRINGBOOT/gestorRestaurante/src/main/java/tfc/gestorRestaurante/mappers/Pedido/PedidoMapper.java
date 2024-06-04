package tfc.gestorRestaurante.mappers.Pedido;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.mappers.detallePedido.DetallePedidoMapper;
import tfc.gestorRestaurante.models.entity.Pedido;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.services.User.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Mapper para la entidad Pedido y su correspondiente DTO.
 * Esta clase utiliza ModelMapper para mapear entre la entidad Pedido y el DTO.
 */
@Component
@AllArgsConstructor
public class PedidoMapper
{
    @Autowired
    private DetallePedidoMapper DetallePedidoMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    /**
     * Mapea de la entidad Pedido a PedidoDTO.
     * @param pedido La entidad Pedido a ser mapeada.
     * @return El PedidoDTO mapeado.
     */
    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
        return pedidoDTO;
    }

    /**
     * Mapea de PedidoDTO a la entidad Pedido.
     * Si el PedidoDTO tiene un userId, también establece la entidad User correspondiente en el Pedido.
     * @param pedidoDTO El PedidoDTO a ser mapeado.
     * @return La entidad Pedido mapeada.
     */
    public Pedido fromDTO(PedidoDTO pedidoDTO) {
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        if (pedidoDTO.getUserId() != null) {
            User user = userService.findById(pedidoDTO.getUserId()).orElse(null);
            pedido.setUser(user);
        }
        return pedido;
    }

    /**
     * Mapea una lista de entidades Pedido a una lista de PedidoDTOs.
     * @param pedidos La lista de entidades Pedido a ser mapeada.
     * @return La lista de PedidoDTOs mapeados.
     */
    public List<PedidoDTO> toDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Mapea una lista de entidades Pedido a una lista de PedidoDTOs.
     * @param pedidos La lista de entidades Pedido a ser mapeada.
     * @return La lista de PedidoDTOs mapeados.
     */
    public List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}