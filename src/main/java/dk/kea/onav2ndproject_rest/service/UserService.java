package dk.kea.onav2ndproject_rest.service;


import dk.kea.onav2ndproject_rest.JwtTokenManager;
import dk.kea.onav2ndproject_rest.config.SecurityConfiguration;
import dk.kea.onav2ndproject_rest.dto.UserConverter;
import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.UserEventDetailsRepository;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements IUserService{

    private UserRepository userRepository;
    private JwtTokenManager jwtTokenManager;
    private UserConverter userConverter;
    private UserEventDetailsRepository userEventDetailsRepository;

    @Override
    public Set<User> findAll() {
        Set<User> set = new HashSet<>();
        userRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public User save(User user) {
//        if(user.getPassword() == null) {
            PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
            user.setPassword(pw.encode(user.getPassword()));
//        }
        return userRepository.save(user);
    }

    @Override
    public void delete(User object) {
            userRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public List<User> findByName(String name) {
        System.out.println("Userservice called findByName with argument: " + name);
        return userRepository.findByUsername(name);
    }

    @Override
    public UserDTO findByToken(String token) {
        String username = jwtTokenManager.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).get(0);
        return userConverter.toDTO(user);
    }
}
