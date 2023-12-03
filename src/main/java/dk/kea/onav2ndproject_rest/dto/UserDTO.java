package dk.kea.onav2ndproject_rest.dto;

import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.entity.Role;

public record UserDTO(int id, String name, String username, Role role, String email, Boolean notifications, Department department ) {
}
