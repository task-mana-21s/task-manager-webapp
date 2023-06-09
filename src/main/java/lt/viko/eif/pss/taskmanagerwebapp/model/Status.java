package lt.viko.eif.pss.taskmanagerwebapp.model;

import jakarta.persistence.*;
import lombok.*;

/**

 Represents the status of a task.
 */
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "status_id")
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('to-do', 'doing', 'done') DEFAULT 'to-do'")
    private StatusEnum status;

    /**

     Enumeration of possible status values.
     */
    public enum StatusEnum {
        TO_DO, DOING, DONE
    }

    /**

     Gets the status enum value.
     @return The status enum value.
     */
    public Object getStatusEnum() {
        return status;
    }

    /**

     Sets the status enum value.
     @param statusEnum The status enum value to set.
     */
    public void setStatusEnum(StatusEnum statusEnum) {
        this.status = statusEnum;
    }
}
