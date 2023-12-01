package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
