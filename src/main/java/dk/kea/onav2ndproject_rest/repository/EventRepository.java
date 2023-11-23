package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
