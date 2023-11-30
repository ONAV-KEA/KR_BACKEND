package dk.kea.onav2ndproject_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
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
    private Boolean notifications;
    @ManyToOne
@JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "user")
    private Set<UserEventDetails> userEventDetails;

}
