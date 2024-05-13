package tfc.gestorRestaurante.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tfc.gestorRestaurante.dto.User.Athentication.LoginUserDTO;
import tfc.gestorRestaurante.dto.User.Athentication.RegisterUserDTO;
import tfc.gestorRestaurante.exception.User.Response;
import tfc.gestorRestaurante.mappers.UserMapper;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.services.Authentication.IAuthenticationService;
import tfc.gestorRestaurante.models.services.User.IUserService;
import tfc.gestorRestaurante.security.JwtTokenProvider;

@RequestMapping("/auth")
@RestController
public class AuthenticationController
{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody RegisterUserDTO registerUserDto)
    {
        User user = userMapper.fromDTO(registerUserDto);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<Response>(Response.noErrorResponse(message), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUserDTO loginUserDto)
    {
        User loginUser = userMapper.fromDTO(loginUserDto);

        User authenticatedUser = authenticationService.authenticate(loginUser);

        String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

        //LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtToken);
    }
}
