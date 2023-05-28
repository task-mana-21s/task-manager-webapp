package lt.viko.eif.pss.taskmanagerwebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long user_id;
    @Column(unique=true)
    private String username;
    private String password;
    private String email;
    private Long role_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
