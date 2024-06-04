package tfc.gestorRestaurante.models.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IRoleRepository;
import tfc.gestorRestaurante.models.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa la interfaz IUserService.
 * Proporciona métodos para manipular y obtener información de los usuarios.
 */
@Service
public class UserService implements IUserService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    /**
     * Elimina un usuario.
     * @param user Un User que representa el usuario a eliminar.
     */
    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    /**
     * Obtiene el rol de un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un Long que representa el ID del rol del usuario.
     */
    @Override
    public Long getRolById(Long id) {
        return userRepository.rolesUser(id);
    }

    /**
     * Encuentra el ID de un usuario por su correo electrónico.
     * @param email El correo electrónico del usuario a encontrar.
     * @return Un Long que representa el ID del usuario.
     */
    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.idUserByEmail(email);
    }

    /**
     * Obtiene todos los usuarios.
     * @return Una lista de User que representa todos los usuarios.
     */
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Encuentra un usuario por su nombre de usuario.
     * @param username El nombre de usuario del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Encuentra un usuario por su correo electrónico.
     * @param email El correo electrónico del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Encuentra un usuario por su ID.
     * @param id El ID del usuario a encontrar.
     * @return Un Optional<User> que representa el usuario encontrado.
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Encuentra usuarios por su ciudad.
     * @param city La ciudad de los usuarios a encontrar.
     * @return Una lista de User que representa los usuarios encontrados.
     */
    @Override
    public List<User> findByCity(String city) {
        return null;
    }

    /**
     * Agrega un nuevo usuario.
     * @param user Un User que representa el usuario a agregar.
     * @param rolIds Una lista de Long que representa los IDs de los roles del usuario.
     * @return Un User que representa el usuario agregado.
     */
    @Override
    public User addUser(User user, List<Long> rolIds)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRoles((List<Role>)roleRepository.findAllById(rolIds));

        return userRepository.save(user);
    }
}