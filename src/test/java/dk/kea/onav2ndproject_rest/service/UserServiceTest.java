package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.JwtTokenManager;
import dk.kea.onav2ndproject_rest.config.SecurityConfiguration;
import dk.kea.onav2ndproject_rest.dto.UserConverter;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.exception.UserNotFoundException;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository mockedUserRepository;

    UserService userService;

    @Autowired
    UserConverter userConverter;

    @BeforeEach
    void init(){
        PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
        MockitoAnnotations.initMocks(this);
        User u1 = new User();
        u1.setId(1);
        u1.setName("User1");
        u1.setPassword(pw.encode("1234"));

        User u2 = new User();
        u2.setId(2);
        u2.setName("User2");
        u2.setPassword(pw.encode("1234"));

        List<User> userList = List.of(u1, u2);

        Mockito.when(mockedUserRepository.findAll()).thenReturn(userList);
        Mockito.when(mockedUserRepository.findById(1)).thenReturn(java.util.Optional.of(u1));
        Mockito.when(mockedUserRepository.findById(2)).thenReturn(java.util.Optional.of(u2));
        doThrow(new UserNotFoundException("User does not exist with id: " + 3)).when(mockedUserRepository).findById(42);

        Mockito.when(mockedUserRepository.save(ArgumentMatchers.any(User.class))).thenAnswer((Answer) invocation -> {
            Object[] args = invocation.getArguments();
            if (args.length > 0 && args[0] instanceof User){
                User u = (User) args[0];
                if(u.getId()==0){
                    u.setId(3);
                }
                return u;
            } else{
                throw new IllegalArgumentException("Wrong argument");
            }
        });

        userService = new UserService(mockedUserRepository, userConverter);

    }

    @Test
    void getAllUsers() {
        assertEquals(2, userService.findAll().size());
    }

    @Test
    void getUserById() {
        assertEquals("User1", userService.findById(1).get().getName());
        assertEquals("User2", userService.findById(2).get().getName());
        assertThrows(UserNotFoundException.class, () -> userService.findById(42));
    }

    @Test
    void createUser() {
        PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
        User u3 = new User();
        u3.setId(3);
        u3.setName("User3");
        u3.setPassword(pw.encode("1234"));
        assertEquals(3, userService.save(u3).getId());
    }

    @Test
    void deleteUser() {
        User u3 = new User();
        u3.setId(3);
        u3.setName("User3");
        userService.delete(u3);
        assertEquals(2, userService.findAll().size());
        assertThrows(UserNotFoundException.class, () -> userService.findById(42));
    }



}