package dk.kea.onav2ndproject_rest.config;

import dk.kea.onav2ndproject_rest.entity.Role;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setName("Anders");
        user1.setId(1);
        user1.setEmail("anders@mail.dk");
        user1.setRole(Role.EMPLOYEE);
        user1.setPassword("1234");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Benny");
        user2.setId(2);
        user2.setEmail("benny@mail.dk");
        user2.setRole(Role.MANAGER);
        user2.setPassword("5678");
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("Carla");
        user3.setId(3);
        user3.setEmail("carla@mail.dk");
        user3.setRole(Role.EMPLOYEE);
        user3.setPassword("9012");
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("Dennis");
        user4.setId(4);
        user4.setEmail("dennis@mail.dk");
        user4.setRole(Role.HEADCHEF);
        user4.setPassword("3456");
        userRepository.save(user4);

        User user5 = new User();
        user5.setName("Eva");
        user5.setId(5);
        user5.setEmail("eva@mail.dk");
        user5.setRole(Role.EMPLOYEE);
        user5.setPassword("7890");
        userRepository.save(user5);
    }
}
