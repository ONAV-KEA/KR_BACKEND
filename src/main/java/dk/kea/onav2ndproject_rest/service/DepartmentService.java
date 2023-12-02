package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.exception.DepartmentNotFoundException;
import dk.kea.onav2ndproject_rest.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> findAll () {
        return departmentRepository.findAll();
    }

    public Optional<Department> findById (int id) {
        return Optional.ofNullable(departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department does not exist with id: " + id)));
    }
}
