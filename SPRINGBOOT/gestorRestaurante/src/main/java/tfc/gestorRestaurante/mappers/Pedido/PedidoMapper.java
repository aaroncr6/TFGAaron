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

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
        return pedidoDTO;
    }

    public Pedido fromDTO(PedidoDTO pedidoDTO) {
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        if (pedidoDTO.getUserId() != null) {
            User user = userService.findById(pedidoDTO.getUserId()).orElse(null);
            pedido.setUser(user);
        }
        return pedido;
    }

    public List<PedidoDTO> toDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
    return pedidos.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}
}