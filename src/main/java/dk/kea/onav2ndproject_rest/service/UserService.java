package dk.kea.onav2ndproject_rest.service;


import dk.kea.onav2ndproject_rest.JwtTokenManager;
import dk.kea.onav2ndproject_rest.config.SecurityConfiguration;
import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.dto.UserConverter;
import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.exception.UserNotFoundException;
import dk.kea.onav2ndproject_rest.repository.UserEventDetailsRepository;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userConverter::toDTO);
    }

    @Override
    public Set<User> findAll() {
        Set<User> set = new HashSet<>();
        userRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public User save(User user) {
            PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
            user.setPassword(pw.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        user.setId(0);
        user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
        user = userRepository.save(user);
        return userConverter.toDTO(user);
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
        Optional<User> user = userRepository.findById(aLong);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with id: " + aLong);
        }
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
