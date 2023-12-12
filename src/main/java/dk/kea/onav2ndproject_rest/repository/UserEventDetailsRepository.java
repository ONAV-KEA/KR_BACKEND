package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.Event;
import dk.kea.onav2ndproject_rest.entity.User;
import dk.kea.onav2ndproject_rest.entity.UserEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventDetailsRepository extends JpaRepository<UserEventDetails, Integer> {
    @Query("SELECT ued.user FROM UserEventDetails ued WHERE ued.participating = true AND ued.event.id = :eventId")
    List<User> findParticipatingUsersByEventId(@Param("eventId") int eventId);

    @Query("SELECT ued.additionalNotes FROM UserEventDetails ued WHERE ued.user.id = :userId AND ued.event.id = :eventId")
    List<String> findAdditionalNotesByUserIdAndEventId(@Param("userId") int userId, @Param("eventId") int eventId);

    Optional<UserEventDetails> findByEventIdAndUserId(Integer eventId, int userId);
    @Query("SELECT CASE WHEN COUNT(ued) > 0 THEN true ELSE false END FROM UserEventDetails ued WHERE ued.user.id = :userId AND ued.event.id = :eventId AND ued.participating = true")
    boolean isUserParticipatingInEvent(@Param("userId") int userId, @Param("eventId") int eventId);

    void deleteByUserId(int userId);
}

