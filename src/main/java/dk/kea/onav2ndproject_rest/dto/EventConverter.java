package dk.kea.onav2ndproject_rest.dto;

import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {
    public Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.name());
        event.setStartDate(eventDTO.startDate());
        event.setEndDate(eventDTO.endDate());
        event.setDescription(eventDTO.description());
        event.setLocation(eventDTO.location());
        event.setImgRef(eventDTO.imgRef());
        event.setDepartments(eventDTO.departments());
        return event;
    }

    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getStartDate(),
                event.getEndDate(),
                event.getDescription(),
                event.getLocation(),
                event.getImgRef(),
                event.getDepartments()
        );
    }
}
