package dk.kea.onav2ndproject_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private Role role;
    private String email;
    private Boolean notications;
    @ManyToOne
@JoinColumn(name = "department_id")
    private Department department;
    @ManyToMany
    @JoinTable(
            name="user_event",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<Event> event;
}
