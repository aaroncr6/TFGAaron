package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.DetallePedido;

import java.util.List;


@Repository
@Hidden
public interface IDetallePedidoRepository extends CrudRepository<DetallePedido, Long>
{
    List<DetallePedido> findAllByPedidoId(Long pedidoId);
}
