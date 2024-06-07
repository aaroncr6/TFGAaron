package tfc.gestorRestaurante.models.services.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IUserRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Clase de prueba para UserService.
 * Esta clase utiliza Mockito para simular la dependencia IUserRepository.
 */
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    /**
     * Método de configuración que inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba del método findById, de la clase UserService.
     * Esta prueba verifica que el método findById llama al método findById del IUserRepository con el id correcto.
     * Se crea un User de prueba con un id específico, y se simula la respuesta del repositorio.
     * Luego se llama al método findById del UserService, y se verifica que el método findById del IUserRepository fue llamado con el id correcto.
     */
    @Test
    void testFindById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.findById(id);

        verify(userRepository).findById(id);
    }

    /**
     * Prueba del método findByUsername, de la clase UserService.
     * Esta prueba verifica que el método findByUsername llama al método findByUsername del IUserRepository con el nombre de usuario correcto.
     * Se crea un User de prueba con un nombre de usuario específico, y se simula la respuesta del repositorio.
     * Luego se llama al método findByUsername del UserService, y se verifica que el método findByUsername del IUserRepository fue llamado con el nombre de usuario correcto.
     */
    @Test
    void testFindByUsername() {
        String username = "admin";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.findByUsername(username);

        verify(userRepository).findByUsername(username);
    }
}