package tfc.gestorRestaurante.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.services.User.IUserService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase que se encarga de la generación y validación de los tokens JWT.
 */
@Service
public class JwtTokenProvider
{
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    private IUserService userService;

    /**
     * Logger para registrar eventos de esta clase.
     */
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Naturaleza del Token!!!
    public static final String TOKEN_HEADER = "Authorization"; // Encabezado
    public static final String TOKEN_PREFIX = "Bearer "; // Prefijo, importante este espacio
    public static final String TOKEN_TYPE = "JWT"; // Tipo de Token

    /**
     * Extrae el nombre de usuario del token.
     * @param token El token JWT.
     * @return El nombre de usuario extraído del token.
     */
    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae una reclamación del token.
     * @param token El token JWT.
     * @param claimsResolver La función para resolver la reclamación.
     * @return La reclamación extraída del token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token para un usuario.
     * @param userDetails Los detalles del usuario.
     * @return El token generado.
     */
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token con reclamaciones adicionales para un usuario.
     * @param extraClaims Las reclamaciones adicionales.
     * @param userDetails Los detalles del usuario.
     * @return El token generado.
     */
    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Obtiene el tiempo de expiración del token.
     * @return El tiempo de expiración del token.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construye un token con reclamaciones adicionales, detalles de usuario y tiempo de expiración.
     * @param extraClaims Las reclamaciones adicionales.
     * @param userDetails Los detalles del usuario.
     * @param expiration El tiempo de expiración del token.
     * @return El token construido.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    )
    {
        return Jwts
                .builder()
                //.setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                //.claim("roles", roles)
                /*.claim("roles", userDetails.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )*/
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifica si un token es válido para un usuario.
     * @param token El token JWT.
     * @param userDetails Los detalles del usuario.
     * @return Verdadero si el token es válido, falso en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token ha expirado.
     * @param token El token JWT.
     * @return Verdadero si el token ha expirado, falso en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración de un token.
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todas las reclamaciones de un token.
     * @param token El token JWT.
     * @return Las reclamaciones extraídas del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma para el token.
     * @return La clave de firma para el token.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}