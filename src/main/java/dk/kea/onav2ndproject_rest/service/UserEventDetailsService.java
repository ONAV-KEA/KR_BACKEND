package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.UserEventDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventDetailsService {

    @Autowired
    UserEventDetailsRepository userEventDetailsRepository;

    public List<User> getParticipatingUsersByEventId(Long eventId) {
        return userEventDetailsRepository.findParticipatingUsersByEventId(eventId);
    }
}
