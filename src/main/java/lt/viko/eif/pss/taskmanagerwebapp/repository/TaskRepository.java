package lt.viko.eif.pss.taskmanagerwebapp.repository;


import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByName(String name);
}
