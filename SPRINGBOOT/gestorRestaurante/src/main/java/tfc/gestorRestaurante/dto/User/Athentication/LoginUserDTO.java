package tfc.gestorRestaurante.dto.User.Athentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO
{
    private String email;
    private String password;
    private String username;
}
