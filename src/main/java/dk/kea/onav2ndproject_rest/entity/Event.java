package dk.kea.onav2ndproject_rest.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Lob
    @Column(name="description", length=512)
    private String description;
    private String location;
    @Lob
    @Column(name="img_ref", length=512)
    private String imgRef;
    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<UserEventDetails> userEventDetails;
    @JsonBackReference
    @ManyToMany(mappedBy = "events", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Department> departments = new HashSet<>();

    public Event(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
