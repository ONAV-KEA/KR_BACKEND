package dk.kea.onav2ndproject_rest.service;

import dk.kea.onav2ndproject_rest.dto.UserConverter;
import dk.kea.onav2ndproject_rest.dto.UserDTO;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.repository.UserEventDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventDetailsService {

    @Autowired
    UserEventDetailsRepository userEventDetailsRepository;
    @Autowired
    UserConverter userConverter;

    public List<UserDTO> getParticipatingUsersByEventId(int eventId) {
        List<User> users = userEventDetailsRepository.findParticipatingUsersByEventId(eventId);
        return users.stream().map(user -> userConverter.toDTO(user)).toList();
    }

    public List<String> getAdditionalNotesByUserIdAndEventId(int userId, int eventId) {
        return userEventDetailsRepository.findAdditionalNotesByUserIdAndEventId(userId, eventId);
    }
}
