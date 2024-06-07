package tfc.gestorRestaurante.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tfc.gestorRestaurante.models.services.User.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Clase de prueba para el controlador GestorRestauranteController.
 * Esta clase utiliza la extensión Mockito para simular las dependencias.
 */
@ExtendWith(MockitoExtension.class)
public class GestorRestauranteControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GestorRestauranteController gestorRestauranteController;

    /**
     * Prueba del método getUserIdByEmail, de la clase GestorRestauranteController.
     * Esta prueba verifica que el método getUserIdByEmail devuelve el ID de usuario esperado para un correo electrónico dado.
     * Se utiliza un correo electrónico de prueba y se simula la respuesta del servicio de usuario.
     */
    @Test
    public void testGetUserIdByEmail() {
        String email = "admin";
        Long expectedUserId = 2L;

        // Simulamos la respuesta del servicio de usuario
        when(userService.findUserIdByEmail(email)).thenReturn(expectedUserId);

        // Llamamos al método que estamos probando
        ResponseEntity<Long> response = gestorRestauranteController.getUserIdByEmail(email);

        // Verificamos que la respuesta es la esperada
        assertEquals(expectedUserId, response.getBody());
        assertTrue(true, "Test passed successfully");
    }
}