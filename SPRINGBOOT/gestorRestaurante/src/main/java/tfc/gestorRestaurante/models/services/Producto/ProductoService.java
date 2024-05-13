package tfc.gestorRestaurante.models.services.Producto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.dto.Producto.ProductoDTO;
import tfc.gestorRestaurante.mappers.Producto.ProductoMapper;
import tfc.gestorRestaurante.models.entity.Producto;
import tfc.gestorRestaurante.models.repository.IProductoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService
{
    private final IProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Autowired
    public ProductoService(IProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO findProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDTO(producto);
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.fromDTO(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    @Override
    public ProductoDTO updateProducto(Long id, ProductoDTO productoDTO) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existingProducto.setNombre(productoDTO.getNombre());
        existingProducto.setIngredientes(productoDTO.getIngredientes());
        existingProducto.setDescripcion(productoDTO.getDescripcion());
        existingProducto.setPrecio(productoDTO.getPrecio());
        existingProducto.setNombreCategoria(productoDTO.getNombreCategoria());

        Producto updatedProducto = productoRepository.save(existingProducto);

        return productoMapper.toDTO(updatedProducto);
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
