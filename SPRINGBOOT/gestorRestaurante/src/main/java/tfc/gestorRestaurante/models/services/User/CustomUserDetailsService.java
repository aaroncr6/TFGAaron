package tfc.gestorRestaurante.models.services.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa la interfaz UserDetailsService.
 * Proporciona métodos para cargar detalles de usuario por nombre de usuario y ID.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private IUserService usuarioService;

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     * @param username El nombre de usuario del usuario a cargar.
     * @return Un objeto UserDetails que representa los detalles del usuario.
     * @throws UsernameNotFoundException si no se encuentra el usuario.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    /**
     * Carga los detalles de un usuario por su ID.
     * @param userId El ID del usuario a cargar.
     * @return Un objeto UserDetails que representa los detalles del usuario.
     * @throws UsernameNotFoundException si no se encuentra el usuario.
     */
    public UserDetails loadUserById(Long userId) {
        return usuarioService.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con id: " + userId + " no encontrado"));
    }
}