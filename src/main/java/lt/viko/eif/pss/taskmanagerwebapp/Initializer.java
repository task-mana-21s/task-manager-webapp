package lt.viko.eif.pss.taskmanagerwebapp;

import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import lt.viko.eif.pss.taskmanagerwebapp.util.HashUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
class Initializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Initializer(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) {

        var user = User.builder()
                .username("Jonas Joanitis")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user);
        var user2 = User.builder()
                .username("Petras Petraitis")
                .email("test123@ff.tt")
                .password(HashUtil.encryptPassword("password123"))
                .build();
        userRepository.save(user2);
                var author = Task.builder()
                .name("Dev task")
                .description("Dev and other things")
                .build();
        taskRepository.save(author);

        var author2 = Task.builder()
                .name("Testing task")
                .description("Testing and other things")
                .build();
        taskRepository.save(author2);


        taskRepository.findAll().forEach(System.out::println);
    }
}
