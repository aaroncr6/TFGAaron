package tfc.gestorRestaurante.models.services.Authentication;

import tfc.gestorRestaurante.models.entity.User;

/**
 * Interfaz que define los métodos de la clase AuthenticationService
 */
public interface IAuthenticationService
{
    public User signup(User newUser);

    public User authenticate(User user);
}
