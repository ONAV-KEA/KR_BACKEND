package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.UserEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEventDetailsRepository extends JpaRepository<UserEventDetails, Long> {
    Optional<UserEventDetails> findByEventIdAndUserId(Integer eventId, Long userId);
}
