package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            List<User> users = userService.findByName(username);
            if (!users.isEmpty()) {
                return users.get(0).getId();
            }
        }
        return -1;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            List<User> users = userService.findByName(username);
            if (!users.isEmpty()) {
                return users.get(0);
            }
        }
        return null;
    }
}
