package dk.kea.onav2ndproject_rest.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String location;
    private String imgRef;
    @ManyToMany(mappedBy = "event")
    private Set<User> users;
    @ManyToMany(mappedBy = "events")
    private Set<Department> departments;
}
