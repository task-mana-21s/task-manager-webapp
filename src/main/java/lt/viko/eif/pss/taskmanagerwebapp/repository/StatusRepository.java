package lt.viko.eif.pss.taskmanagerwebapp.repository;

import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**

 Repository interface for managing status entities.
 */
public interface StatusRepository extends JpaRepository<Status, Long> {
}
