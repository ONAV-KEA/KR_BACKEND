package dk.kea.onav2ndproject_rest.dto;

import dk.kea.onav2ndproject_rest.entity.Department;

import java.time.LocalDate;
import java.util.Set;

public record EventDTO(int id, String name, LocalDate startDate, LocalDate endDate, String description, String location, String imgRef, Set<Department> departments) {
}
