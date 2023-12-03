package dk.kea.onav2ndproject_rest.dto;

import dk.kea.onav2ndproject_rest.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setUsername(userDTO.username());
        user.setRole(userDTO.role());
        user.setEmail(userDTO.email());
        user.setNotifications(userDTO.notifications());
        user.setDepartment(userDTO.department());
        return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                user.getNotifications(),
                user.getDepartment()
        );
    }
}
