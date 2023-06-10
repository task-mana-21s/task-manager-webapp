package lt.viko.eif.pss.taskmanagerwebapp.repository;

import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**

 UserRepository interface that extends JPARepository to manage User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
