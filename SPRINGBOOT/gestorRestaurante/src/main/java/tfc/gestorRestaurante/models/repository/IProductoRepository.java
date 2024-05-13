package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.Producto;

@Repository
@Hidden
public interface IProductoRepository extends CrudRepository <Producto,Long>
{

}
