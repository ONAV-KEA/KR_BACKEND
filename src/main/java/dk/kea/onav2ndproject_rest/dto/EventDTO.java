package dk.kea.onav2ndproject_rest.dto;

import java.time.LocalDate;

public record EventDTO(int id, String name, LocalDate startDate, LocalDate endDate, String description, String location, String imgRef) {
}
