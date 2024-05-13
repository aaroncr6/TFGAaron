package tfc.gestorRestaurante.dto.User.Athentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO
{
    private String password;

    private String nif;

    private String name;

    private String surname;

    private String email;

    private List<Long> rolIds;
}
