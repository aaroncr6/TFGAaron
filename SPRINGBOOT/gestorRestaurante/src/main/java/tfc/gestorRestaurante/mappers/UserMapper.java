package tfc.gestorRestaurante.mappers;


import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tfc.gestorRestaurante.dto.User.Athentication.LoginUserDTO;
import tfc.gestorRestaurante.dto.User.Athentication.RegisterUserDTO;
import tfc.gestorRestaurante.dto.User.UserDTORequest;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IRoleRepository;

@Component
@NoArgsConstructor
public class UserMapper
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    IRoleRepository roleRepository;

    public User fromDTO(LoginUserDTO userDTO)
    {
        return modelMapper.map(userDTO, User.class);
    }

    public User fromDTO(UserDTORequest userDTO)
    {
        return modelMapper.map(userDTO, User.class);
    }

    public User fromDTO(RegisterUserDTO registerUserDTO)
    {
        //Mapea los nombres de la lista Actores a una lista de String.
        modelMapper.typeMap(RegisterUserDTO.class, User.class).addMappings(mapper -> {
            mapper.using(new RolesListConverter (roleRepository)).map(RegisterUserDTO::getRolIds, User::setRoles);
        });

        return modelMapper.map(registerUserDTO, User.class);
    }
}
