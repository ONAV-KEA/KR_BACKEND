package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByUsername(String name);
}
