package tfc.gestorRestaurante.models.services.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.dto.Producto.ProductoDTO;
import tfc.gestorRestaurante.mappers.Producto.ProductoMapper;
import tfc.gestorRestaurante.models.entity.Producto;
import tfc.gestorRestaurante.models.repository.IProductoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de servicio que implementa la interfaz IProductoService.
 * Proporciona métodos para manipular y obtener información de los productos.
 */
@Service
public class ProductoService implements IProductoService
{
    private final IProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    /**
     * Constructor de la clase ProductoService.
     * @param productoRepository Repositorio para acceder a los datos de los productos.
     * @param productoMapper Mapper para convertir entre Producto y ProductoDTO.
     */
    @Autowired
    public ProductoService(IProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    /**
     * Obtiene todos los productos.
     * @return Una lista de ProductoDTO.
     */
    @Override
    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Encuentra un producto por su ID.
     * @param id El ID del producto a encontrar.
     * @return Un ProductoDTO del producto encontrado.
     * @throws RuntimeException si no se encuentra el producto.
     */
    @Override
    public ProductoDTO findProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDTO(producto);
    }

    /**
     * Crea un nuevo producto.
     * @param productoDTO El DTO del producto a crear.
     * @return Un ProductoDTO del producto creado.
     */
    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.fromDTO(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param productoDTO El DTO del producto con los datos actualizados.
     * @return Un ProductoDTO del producto actualizado.
     * @throws RuntimeException si no se encuentra el producto.
     */
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

    /**
     * Elimina un producto.
     * @param id El ID del producto a eliminar.
     */
    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}