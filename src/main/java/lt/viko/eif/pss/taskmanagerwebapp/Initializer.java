package lt.viko.eif.pss.taskmanagerwebapp;

import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final TaskRepository repository;

    public Initializer(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Testing", "Dev", "Analysis").forEach(name ->
                repository.save(new Task(name))
        );


        repository.findAll().forEach(System.out::println);
    }
}
