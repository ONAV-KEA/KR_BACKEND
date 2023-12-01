package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> findAll () {
        return departmentRepository.findAll();
    }
}
