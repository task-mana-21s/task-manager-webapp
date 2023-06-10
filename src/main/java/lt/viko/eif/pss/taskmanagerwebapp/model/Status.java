package lt.viko.eif.pss.taskmanagerwebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "status")
public class Status {
    @Id
    private Long Id;

    @Column(unique=true)
    private String status;
}