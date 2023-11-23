package dk.kea.onav2ndproject_rest.dto;

import java.time.LocalDate;

public record EventDTO(String name, LocalDate startDate, LocalDate endDate, String description, String location, String imgRef) {
}
