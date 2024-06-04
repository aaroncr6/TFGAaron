package tfc.gestorRestaurante.models.services.Authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IUserRepository;
import java.util.NoSuchElementException;

import java.time.LocalDateTime;

/**
 * Clase de servicio para la autenticación de usuarios.
 */
@Setter
@Getter
@NoArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Método para registrar un nuevo usuario.
     * Codifica la contraseña del usuario y establece la fecha de creación y el nombre de usuario.
     * @param newUser El nuevo usuario a registrar.
     * @return El usuario registrado.
     */
    @Override
    public User signup(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setCreationDate(LocalDateTime.now());
        newUser.setUsername(newUser.getEmail());

        return userRepository.save(newUser);
    }

    /**
     * Método para autenticar a un usuario.
     * Autentica al usuario con el administrador de autenticación y luego busca al usuario en el repositorio.
     * @param user El usuario a autenticar.
     * @return El usuario autenticado.
     * @throws NoSuchElementException si no se encuentra el usuario.
     */
    @Override
    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return userRepository.findByEmail(user.getEmail())
                .orElseThrow();
    }
}