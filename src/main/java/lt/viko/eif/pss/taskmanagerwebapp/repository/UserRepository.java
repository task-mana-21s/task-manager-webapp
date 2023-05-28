package lt.viko.eif.pss.taskmanagerwebapp.repository;

import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
