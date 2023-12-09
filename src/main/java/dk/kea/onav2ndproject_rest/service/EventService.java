package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.dto.EventConverter;
import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.dto.UserEventResponseDTO;
import dk.kea.onav2ndproject_rest.entity.Department;
import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.entity.UserEventDetails;
import dk.kea.onav2ndproject_rest.exception.EventNotFoundException;
import dk.kea.onav2ndproject_rest.exception.UserNotFoundException;
import dk.kea.onav2ndproject_rest.repository.DepartmentRepository;
import dk.kea.onav2ndproject_rest.repository.EventRepository;
import dk.kea.onav2ndproject_rest.repository.UserEventDetailsRepository;
import dk.kea.onav2ndproject_rest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventConverter eventConverter;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserEventDetailsRepository userEventDetailsRepository;
    @Autowired
    UserRepository userRepository;

    public Page<EventDTO> getAllEvents(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(eventConverter::toDTO);
    }

    public EventDTO getEventById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return eventConverter.toDTO(event.get());
        } else {
            throw new EventNotFoundException("Event does not exist with id " + id);
        }
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventConverter.toEntity(eventDTO);
        event.setId(0);

        // set departments to existing entities from db
        Set<Department> departments = new HashSet<>();
        for (Department department : event.getDepartments()){
            Optional<Department> departmentOptional = departmentService.findById(department.getId());
            if (departmentOptional.isPresent()){
                Department existingDepartment = departmentOptional.get();
                departments.add(existingDepartment);
                existingDepartment.getEvents().add(event); // set the event to the department
            }
        }
        event.setDepartments(departments);

        Event savedEvent = eventRepository.save(event);
        return eventConverter.toDTO(savedEvent);
    }

    public EventDTO updateEvent(int id, EventDTO eventDTO){
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()){
            Event eventToUpdate = eventOptional.get();

            for (Department department : eventToUpdate.getDepartments()) {
                department.getEvents().remove(eventToUpdate);
            }
            eventToUpdate.getDepartments().clear();

            eventToUpdate.setName(eventDTO.name());
            eventToUpdate.setDescription(eventDTO.description());
            eventToUpdate.setStartDate(eventDTO.startDate());
            eventToUpdate.setEndDate(eventDTO.endDate());
            eventToUpdate.setImgRef(eventDTO.imgRef());
            eventToUpdate.setLocation(eventDTO.location());

            Event updatedEvent = eventConverter.toEntity(eventDTO);
            for (Department department : updatedEvent.getDepartments()){
                Optional<Department> departmentOptional = departmentService.findById(department.getId());
                if (departmentOptional.isPresent()){
                    Department existingDepartment = departmentOptional.get();
                    existingDepartment.getEvents().add(eventToUpdate); // Add the event to the department
                    eventToUpdate.getDepartments().add(existingDepartment); // Add the department to the event
                }
            }

            Event savedEvent = eventRepository.save(eventToUpdate);
            return eventConverter.toDTO(savedEvent);
        } else {
            throw new EventNotFoundException("Event does not exist with id " + id);
        }
    }

    @Transactional
    public void deleteEventById(int id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()){
            Event eventToRemove = event.get();
            for (Department department : eventToRemove.getDepartments()) {
                department.getEvents().remove(eventToRemove);
            }
            userEventDetailsRepository.findParticipatingUsersByEventId(id).forEach(user -> {
                userEventDetailsRepository.deleteByUserId(user.getId());
            });
            eventRepository.delete(eventToRemove);
        } else {
            throw new EventNotFoundException("Event does not exist with id " + id);
        }
    }

    public Page<EventDTO> findAllByDepartmentId(int id, Pageable pageable) {
        Page<Event> events = eventRepository.findAllByDepartmentsId(id, pageable);
        return events.map(eventConverter::toDTO);
    }

    public void respondToEvent(Integer eventId, int userId, UserEventResponseDTO response) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event does not exist with id: " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with id: " + userId));

        UserEventDetails userEventDetails = userEventDetailsRepository.findByEventIdAndUserId(eventId, userId)
                .orElse(new UserEventDetails());

        userEventDetails.setEvent(event);
        userEventDetails.setUser(user);
        userEventDetails.setParticipating(response.participating());
        userEventDetails.setAdditionalNotes(response.additionalNote());

        userEventDetailsRepository.save(userEventDetails);
    }
}
