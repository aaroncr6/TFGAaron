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

/**
 * Clase Mapper para la entidad User y sus correspondientes DTOs.
 * Esta clase utiliza ModelMapper para mapear entre la entidad User y los DTOs.
 */
@Component
@NoArgsConstructor
public class UserMapper
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    IRoleRepository roleRepository;

    /**
     * Mapea de LoginUserDTO a la entidad User.
     * @param userDTO El LoginUserDTO a ser mapeado.
     * @return La entidad User mapeada.
     */
    public User fromDTO(LoginUserDTO userDTO)
    {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Mapea de UserDTORequest a la entidad User.
     * @param userDTO El UserDTORequest a ser mapeado.
     * @return La entidad User mapeada.
     */
    public User fromDTO(UserDTORequest userDTO)
    {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Mapea de RegisterUserDTO a la entidad User.
     * Este método también mapea los IDs de rol de RegisterUserDTO a una lista de entidades Role en User.
     * @param registerUserDTO El RegisterUserDTO a ser mapeado.
     * @return La entidad User mapeada.
     */
    public User fromDTO(RegisterUserDTO registerUserDTO)
    {
        // Mapea los nombres de la lista de roles a una lista de String.
        modelMapper.typeMap(RegisterUserDTO.class, User.class).addMappings(mapper -> {
            mapper.using(new RolesListConverter (roleRepository)).map(RegisterUserDTO::getRolIds, User::setRoles);
        });

        return modelMapper.map(registerUserDTO, User.class);
    }
}