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

@ExtendWith(MockitoExtension.class)
public class GestorRestauranteControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GestorRestauranteController gestorRestauranteController;

    /**
     * Prueba del método getUserIdByEmail, de la clase GestorRestauranteController.
     */
    @Test
    public void testGetUserIdByEmail() {
        String email = "admin";
        Long expectedUserId = 2L;

        when(userService.findUserIdByEmail(email)).thenReturn(expectedUserId);

        ResponseEntity<Long> response = gestorRestauranteController.getUserIdByEmail(email);

        assertEquals(expectedUserId, response.getBody());
        assertTrue(true, "Test passed successfully");
    }
}