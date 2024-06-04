package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.Pedido;

import java.util.List;


/**
 * Repositorio para la entidad Pedido.
 * Extiende de CrudRepository para obtener métodos CRUD.
 */
@Repository
@Hidden
public interface IPedidoRepository extends CrudRepository<Pedido, Long>
{

    List<Pedido> findPedidosByUserId(Long userId);
}
