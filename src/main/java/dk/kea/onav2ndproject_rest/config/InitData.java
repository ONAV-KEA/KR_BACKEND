package dk.kea.onav2ndproject_rest.config;

import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.entity.Role;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.DepartmentRepository;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Department> departments = new ArrayList<>();

        if (departmentRepository.count() == 0) {
            System.out.println("InitData.run: 2");
            departments = createDepartments();
        }

        if (userRepository.count() == 0) {
            System.out.println("InitData.run: 1");
            createUsers(departments);
        }
    }

    private List<Department> createDepartments(){
        Department department1 = new Department();
        department1.setId(1);
        department1.setName("IT");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setId(2);
        department2.setName("Økonomi");
        departmentRepository.save(department2);

        Department department3 = new Department();
        department3.setId(3);
        department3.setName("Advokat");
        departmentRepository.save(department3);

        Department department4 = new Department();
        department4.setId(4);
        department4.setName("Køkken");
        departmentRepository.save(department4);

        Department department5 = new Department();
        department5.setId(5);
        department5.setName("P&D");
        departmentRepository.save(department5);

        return Arrays.asList(department1, department2, department3, department4, department5);
    }

    private void createUsers(List<Department> departments) {

        System.out.println("InitData.createUsers: 2");
        User user1 = new User();
        user1.setName("Anders");
        user1.setId(1);
        user1.setEmail("anders@mail.dk");
        user1.setRole(Role.EMPLOYEE);
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setUsername("anders");
        user1.setDepartment(departments.get(0));
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Benny");
        user2.setId(2);
        user2.setEmail("benny@mail.dk");
        user2.setRole(Role.MANAGER);
        user2.setPassword(passwordEncoder.encode("5678"));
        user2.setUsername("benny");
        user2.setDepartment(departments.get(1));
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("Carla");
        user3.setId(3);
        user3.setEmail("carla@mail.dk");
        user3.setRole(Role.EMPLOYEE);
        user3.setPassword(passwordEncoder.encode("9012"));
        user3.setUsername("carla");
        user3.setDepartment(departments.get(2));
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("Dennis");
        user4.setId(4);
        user4.setEmail("dennis@mail.dk");
        user4.setRole(Role.HEADCHEF);
        user4.setPassword(passwordEncoder.encode("3456"));
        user4.setUsername("dennis");
        user4.setDepartment(departments.get(3));
        userRepository.save(user4);

        User user5 = new User();
        user5.setName("Eva");
        user5.setId(5);
        user5.setEmail("eva@mail.dk");
        user5.setRole(Role.EMPLOYEE);
        user5.setPassword(passwordEncoder.encode("7890"));
        user5.setUsername("eva");
        user5.setDepartment(departments.get(4));
        userRepository.save(user5);
    }
}
