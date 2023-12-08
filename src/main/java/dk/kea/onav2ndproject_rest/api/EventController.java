package dk.kea.onav2ndproject_rest.api;

import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.dto.UserEventResponseDTO;
import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.entity.Role;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.service.AuthenticationService;
import dk.kea.onav2ndproject_rest.service.EventService;
import dk.kea.onav2ndproject_rest.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventService.getAllEvents(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable int id) {
        EventDTO event = eventService.getEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        User currentUser = authenticationService.getCurrentUser();
        if (currentUser == null || currentUser.getRole() != Role.MANAGER) {
            return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
        }

        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody EventDTO eventDTO) {
        User currentUser = authenticationService.getCurrentUser();
        if (currentUser == null || currentUser.getRole() != Role.MANAGER) {
            return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
        }

        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        User currentUser = authenticationService.getCurrentUser();
        if (currentUser == null || currentUser.getRole() != Role.MANAGER) {
            return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
        }


        eventService.deleteEventById(id);
        return new ResponseEntity<>("Event with id " + id + " was deleted", HttpStatus.OK);
    }

    @GetMapping("/department/{id}")
    public Page<EventDTO> getAllEventsByDepartmentId(@PathVariable int id, Pageable pageable) {
        return eventService.findAllByDepartmentId(id, pageable);
    }

    @PostMapping("/{eventId}/respond")
    public ResponseEntity<?> respondToEvent(@PathVariable int eventId, @RequestBody UserEventResponseDTO response) {
        int userId = authenticationService.getCurrentUserId();
        if (userId == -1) {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "User not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
        }

        try {
            eventService.respondToEvent(eventId, userId, response);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Response received successfully");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }



}
