package tfc.gestorRestaurante.mappers;

import org.modelmapper.AbstractConverter;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.repository.IRoleRepository;

import java.util.List;

/**
 * Clase de conversión que mapea una lista de IDs de roles a una lista de entidades de roles.
 * Esta clase extiende de AbstractConverter de ModelMapper.
 */
public class RolesListConverter extends AbstractConverter<List<Long>, List<Role>>
{
    private final IRoleRepository roleRepository;

    /**
     * Constructor de la clase RolesListConverter.
     * @param roleRepository Repositorio de roles, utilizado para buscar roles por sus IDs.
     */
    public RolesListConverter(IRoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    /**
     * Método de conversión que mapea una lista de IDs de roles a una lista de entidades de roles.
     * @param longs Lista de IDs de roles.
     * @return Lista de entidades de roles.
     */
    @Override
    protected List<Role> convert(List<Long> longs)
    {
        return (List<Role>) roleRepository.findAllById(longs);
    }
}