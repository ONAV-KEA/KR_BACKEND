package dk.kea.onav2ndproject_rest.repository;

import dk.kea.onav2ndproject_rest.dto.EventDTO;
import dk.kea.onav2ndproject_rest.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    public Page<Event> findAllByDepartmentsId(int id, Pageable pageable);
}
