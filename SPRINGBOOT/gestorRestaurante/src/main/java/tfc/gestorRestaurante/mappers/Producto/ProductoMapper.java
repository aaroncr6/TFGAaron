package tfc.gestorRestaurante.mappers.Producto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.gestorRestaurante.dto.Producto.ProductoDTO;
import tfc.gestorRestaurante.models.entity.Producto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductoMapper
{
    @Autowired
    private ModelMapper modelMapper;

    public ProductoDTO toDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    public Producto fromDTO(ProductoDTO productoDTO) {
        return modelMapper.map(productoDTO, Producto.class);
    }

    public List<ProductoDTO> toDTO(List<Producto> productos) {
        return productos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
