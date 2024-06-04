package tfc.gestorRestaurante.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.services.User.CustomUserDetailsService;
import java.io.IOException;

/**
 * Filtro de autenticación JWT.
 * Este filtro se encarga de procesar las solicitudes entrantes y validar los tokens JWT.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtTokenProvider jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Método que se ejecuta una vez por cada solicitud.
     * Este método se encarga de procesar la solicitud y validar el token JWT.
     * @param request La solicitud entrante.
     * @param response La respuesta a enviar.
     * @param filterChain La cadena de filtros a aplicar.
     * @throws ServletException Si ocurre un error al procesar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader(JwtTokenProvider.TOKEN_HEADER);

        if (authHeader == null || !authHeader.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Sacamos el token
            String token = getJwtFromRequest(request);

            final String userName = jwtService.extractUsername(token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userName != null && authentication == null)
            {
                User user = (User) customUserDetailsService.loadUserByUsername(userName);

                // Si el token existe y es válido
                if (StringUtils.hasText(token) && jwtService.isTokenValid(token, user))
                {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            user.getRoles(),
                            user.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        }
        catch (Exception exception)
        {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    /**
     * Método que se encarga de extraer el token JWT de la solicitud.
     * @param request La solicitud entrante.
     * @return El token JWT extraído de la solicitud.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // Tomamos la cabecera
        String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
        // Si tiene el prefijo y es de la logitud indicada
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length());
        }
        return null;
    }
}