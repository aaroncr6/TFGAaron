package tfc.gestorRestaurante.models.services.Producto;

import tfc.gestorRestaurante.dto.Producto.ProductoDTO;

import java.util.List;

public interface IProductoService {
    List<ProductoDTO> getAllProductos();


    ProductoDTO findProductoById(Long id);

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO updateProducto(Long id, ProductoDTO productoDTO);

    void deleteProducto(Long id);
}
