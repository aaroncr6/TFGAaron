package tfc.gestorRestaurante.models.services.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.entity.User;
import tfc.gestorRestaurante.models.repository.IRoleRepository;
import tfc.gestorRestaurante.models.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public Long getRolById(Long id) {
        return userRepository.rolesUser(id);
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.idUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByCity(String city) {
        return null;
    }

    @Override
    public User addUser(User user, List<Long> rolIds)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRoles((List<Role>)roleRepository.findAllById(rolIds));

        return userRepository.save(user);
    }
}
