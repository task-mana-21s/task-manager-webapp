package lt.viko.eif.pss.taskmanagerwebapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {


    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    private String description;
/*    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;*/
}