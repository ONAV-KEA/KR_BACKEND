package dk.kea.onav2ndproject_rest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    @JsonManagedReference
    private Set<UserEventDetails> userEventDetails;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
