package tfc.gestorRestaurante.models.services.User;

import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos de la clase UserService.
 * Esta interfaz proporciona una abstracción para el servicio de usuarios.
 */
public interface IUserService
{
    /**
     * Obtiene todos los usuarios.
     * @return Una lista de User que representa todos los usuarios.
     */
    List<User> findAll();

    /**
     * Encuentra un usuario por su nombre de usuario.
     * @param username El nombre de usuario del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    Optional<User> findByUsername(String username);

    /**
     * Encuentra un usuario por su correo electrónico.
     * @param email El correo electrónico del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    Optional<User> findByEmail(String email);

    /**
     * Encuentra un usuario por su ID.
     * @param id El ID del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    Optional<User> findById(Long id);

    /**
     * Encuentra usuarios por su ciudad.
     * @param city La ciudad de los usuarios a encontrar.
     * @return Una lista de User que representa los usuarios encontrados.
     */
    List<User> findByCity(String city);

    /**
     * Agrega un nuevo usuario.
     * @param user Un User que representa el usuario a agregar.
     * @param rolIds Una lista de Long que representa los IDs de los roles del usuario.
     * @return Un User que representa el usuario agregado.
     */
    User addUser(User user, List<Long> rolIds);

    /**
     * Elimina un usuario.
     * @param user Un User que representa el usuario a eliminar.
     */
    void remove(User user);

    /**
     * Obtiene el ID de un rol por su ID.
     * @param id El ID del rol a obtener.
     * @return Un Long que representa el ID del rol.
     */
    Long getRolById(Long id);

    /**
     * Encuentra el ID de un usuario por su correo electrónico.
     * @param email El correo electrónico del usuario a encontrar.
     * @return Un Long que representa el ID del usuario.
     */
    Long findUserIdByEmail(String email);
}