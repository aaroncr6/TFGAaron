package tfc.gestorRestaurante.models.services.Producto;

import tfc.gestorRestaurante.dto.Producto.ProductoDTO;

import java.util.List;

/**
 * Interfaz que define los métodos de la clase ProductoService.
 * Esta interfaz proporciona una abstracción para el servicio de productos.
 */
public interface IProductoService {

    /**
     * Obtiene todos los productos.
     * @return Una lista de ProductoDTO que representa todos los productos.
     */
    List<ProductoDTO> getAllProductos();

    /**
     * Encuentra un producto por su ID.
     * @param id El ID del producto a encontrar.
     * @return Un ProductoDTO que representa el producto encontrado.
     */
    ProductoDTO findProductoById(Long id);

    /**
     * Crea un nuevo producto.
     * @param productoDTO Un ProductoDTO que representa el producto a crear.
     * @return Un ProductoDTO que representa el producto creado.
     */
    ProductoDTO createProducto(ProductoDTO productoDTO);

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param productoDTO Un ProductoDTO que representa el producto con los datos actualizados.
     * @return Un ProductoDTO que representa el producto actualizado.
     */
    ProductoDTO updateProducto(Long id, ProductoDTO productoDTO);

    /**
     * Elimina un producto.
     * @param id El ID del producto a eliminar.
     */
    void deleteProducto(Long id);
}