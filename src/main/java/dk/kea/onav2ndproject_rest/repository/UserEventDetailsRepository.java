package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.entity.UserEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEventDetailsRepository extends JpaRepository<UserEventDetails, Long> {
    @Query("SELECT ued.user FROM UserEventDetails ued WHERE ued.participating = true AND ued.event.id = :eventId")
    List<User> findParticipatingUsersByEventId(int eventId);

    @Query("SELECT ued.additionalNotes FROM UserEventDetails ued WHERE ued.user.id = :userId AND ued.event.id = :eventId")
    List<String> findAdditionalNotesByUserIdAndEventId(@Param("userId") int userId, @Param("eventId") int eventId);

    Optional<UserEventDetails> findByEventIdAndUserId(Integer eventId, Long userId);
}

