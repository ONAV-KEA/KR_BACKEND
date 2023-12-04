package dk.kea.onav2ndproject_rest.api;

import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.service.UserEventDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class UserEventDetailsController {

    @Autowired
    UserEventDetailsService userEventDetailService;

    @GetMapping("/participating/{eventId}")
    public ResponseEntity<List<UserDTO>> getParticipatingUsersByEventId(@PathVariable int eventId){
        return new ResponseEntity<>(userEventDetailService.getParticipatingUsersByEventId(eventId), HttpStatus.OK);
    }

    @GetMapping("/additionalNotes/{userId}/{eventId}")
    public ResponseEntity<List<String>> getAdditionalNotesByUserIdAndEventId(@PathVariable int userId, @PathVariable int eventId){
        return new ResponseEntity<>(userEventDetailService.getAdditionalNotesByUserIdAndEventId(userId, eventId), HttpStatus.OK);
    }

    @GetMapping("/participating/{userId}/{eventId}")
    public ResponseEntity<Boolean> isUserParticipatingInEvent(@PathVariable int userId, @PathVariable int eventId) {
        return new ResponseEntity<>(userEventDetailService.isUserParticipatingInEvent(userId, eventId), HttpStatus.OK);
    }

}
