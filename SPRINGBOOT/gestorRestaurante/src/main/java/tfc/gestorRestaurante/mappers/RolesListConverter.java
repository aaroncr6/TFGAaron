package tfc.gestorRestaurante.mappers;


import org.modelmapper.AbstractConverter;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.repository.IRoleRepository;

import java.util.List;

public class RolesListConverter extends AbstractConverter<List<Long>, List<Role>>
{
    private final IRoleRepository roleRepository;

    public RolesListConverter(IRoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    @Override
    protected List<Role> convert(List<Long> longs)
    {
        return (List<Role>) roleRepository.findAllById(longs);
    }
}
