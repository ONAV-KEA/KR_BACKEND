package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService extends ICrudService<User,Long>{
    List<User> findByName(String name);

    UserDTO findByToken(String token);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO createUser(UserDTO userDTO);
}
