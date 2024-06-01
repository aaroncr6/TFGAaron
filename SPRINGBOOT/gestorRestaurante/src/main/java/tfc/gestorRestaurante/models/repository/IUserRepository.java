package tfc.gestorRestaurante.models.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface IUserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    @Query(value = "SELECT ur.role_id " +
            "FROM user u " +
            "         INNER JOIN user_role ur ON u.id = ur.user_id " +
            "WHERE u.id = ?1", nativeQuery = true)
    Long rolesUser(long idUsuario);


    @Query(value = "SELECT u.id FROM user  u WHERE u.email = :email")
    Long idUserByEmail(@Param("email") String email);
}
