package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.User;

import java.util.List;

public interface IUserService extends ICrudService<User,Long>{
    List<User> findByName(String name);

    UserDTO findByToken(String token);
}
