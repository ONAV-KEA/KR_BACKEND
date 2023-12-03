package dk.kea.onav2ndproject_rest.api;

import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.service.UserEventDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class UserEventDetailsController {

    @Autowired
    UserEventDetailsService userEventDetailService;

    @GetMapping("/participating/{eventId}")
    public ResponseEntity<List<User>> getParticipatingUsersByEventId(@PathVariable long eventId){
        return new ResponseEntity<>(userEventDetailService.getParticipatingUsersByEventId(eventId), HttpStatus.OK);
    }

}
