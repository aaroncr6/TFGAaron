package tfc.gestorRestaurante.models.services.Authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IUserRepository;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User signup(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setCreationDate(LocalDateTime.now());
        newUser.setUsername(newUser.getEmail());

        return userRepository.save(newUser);
    }

    @Override
    public User authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return userRepository.findByEmail(user.getEmail())
                .orElseThrow();
    }
}
