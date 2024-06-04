package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.Producto;

/**
 * Repositorio para la entidad Producto.
 * Extiende de CrudRepository para obtener métodos CRUD.
 */
@Repository
@Hidden
public interface IProductoRepository extends CrudRepository <Producto,Long>
{

}
