package dk.kea.onav2ndproject_rest.config;

import dk.kea.onav2ndproject_rest.entity.Role;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("InitData.run: 1");
            createUsers();
        }
    }

    private void createUsers() {
        System.out.println("InitData.createUsers: 2");
        User user1 = new User();
        user1.setName("Anders");
        user1.setId(31);
        user1.setEmail("anders@mail.dk");
        user1.setRole(Role.EMPLOYEE);
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setUsername("anders");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Benny");
        user2.setId(32);
        user2.setEmail("benny@mail.dk");
        user2.setRole(Role.MANAGER);
        user2.setPassword(passwordEncoder.encode("5678"));
        user2.setUsername("benny");
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("Carla");
        user3.setId(33);
        user3.setEmail("carla@mail.dk");
        user3.setRole(Role.EMPLOYEE);
        user3.setPassword(passwordEncoder.encode("9012"));
        user3.setUsername("carla");
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("Dennis");
        user4.setId(34);
        user4.setEmail("dennis@mail.dk");
        user4.setRole(Role.HEADCHEF);
        user4.setPassword(passwordEncoder.encode("3456"));
        user4.setUsername("dennis");
        userRepository.save(user4);

        User user5 = new User();
        user5.setName("Eva");
        user5.setId(35);
        user5.setEmail("eva@mail.dk");
        user5.setRole(Role.EMPLOYEE);
        user5.setPassword(passwordEncoder.encode("7890"));
        user5.setUsername("eva");
        userRepository.save(user5);
    }
}
