package tfc.gestorRestaurante.models.services.Authentication;

import tfc.gestorRestaurante.models.entity.User;

public interface IAuthenticationService
{
    public User signup(User newUser);

    public User authenticate(User user);
}
