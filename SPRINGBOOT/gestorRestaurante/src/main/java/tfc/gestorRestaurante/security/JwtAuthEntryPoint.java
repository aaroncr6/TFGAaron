package tfc.gestorRestaurante.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Clase que se encarga de gestionar la respuesta de error en caso de que el usuario no esté autorizado.
 * Implementa la interfaz AuthenticationEntryPoint de Spring Security.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint
{
    /**
     * Logger para registrar eventos de esta clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    /**
     * Método que se invoca cuando un usuario intenta acceder a un recurso protegido sin autenticación.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param authException La excepción de autenticación lanzada.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si no se puede manejar la solicitud o la respuesta.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException
    {
        // Log the error message
        logger.error("Unauthorized error: {}", authException.getMessage());
        // Send an HTTP 401 error response
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}