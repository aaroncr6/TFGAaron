package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.Role;

@Repository
@Hidden
public interface IRoleRepository extends CrudRepository<Role, Long>
{
    Role findByName(String name);


}
