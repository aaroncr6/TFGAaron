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

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba del método findById, de la clase UserService.
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