package lt.viko.eif.pss.taskmanagerwebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Column(name = "f_name")
    private String name;
    @Column(length = 2000)
    private String description;
/*    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column(insertable = false)
    private LocalDateTime lastUpdatedAt;*/
    @ManyToOne(cascade=CascadeType.ALL)
    private User user;
}