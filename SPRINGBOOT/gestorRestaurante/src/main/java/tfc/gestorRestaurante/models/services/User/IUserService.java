package tfc.gestorRestaurante.models.services.User;


import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService
{
    List<User> findAll();
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
    List<User> findByCity(String city);

    User addUser(User user, List<Long> rolIds);
    void remove(User user);
    Long getRolById(Long id);

    Long findUserIdByEmail(String email);
}
