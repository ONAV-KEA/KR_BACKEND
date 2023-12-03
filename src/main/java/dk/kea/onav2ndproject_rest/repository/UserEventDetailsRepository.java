package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.entity.UserEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEventDetailsRepository extends JpaRepository<UserEventDetails, Long> {
    @Query("SELECT ued.user FROM UserEventDetails ued WHERE ued.participating = true AND ued.event.id = :eventId")
    List<User> findParticipatingUsersByEventId(Long eventId);
}

