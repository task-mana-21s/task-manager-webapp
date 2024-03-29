package lt.viko.eif.pss.taskmanagerwebapp;

import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.repository.StatusRepository;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import lt.viko.eif.pss.taskmanagerwebapp.util.HashUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
class Initializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    public Initializer(TaskRepository taskRepository, UserRepository userRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... strings) {

        var user = User.builder()
                .username("Giedrius Mauza")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user);

        var user4 = User.builder()
                .username("Simona Baguzyte")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user4);

        var user5 = User.builder()
                .username("Viktorija Jakubcevic")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user5);

        var user6 = User.builder()
                .username("Gabija Poskaite")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user6);

        var user7 = User.builder()
                .username("Evelina Rudzinskaite")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user7);

        var user8 = User.builder()
                .username("Lukas Karalis")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        userRepository.save(user8);

        var user3 = User.builder()
                .username("Sindija Tamulyte")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();

        var user2 = User.builder()
                .username("test")
                .email("test123@ff.tt")
                .password(HashUtil.encryptPassword("123"))
                .build();
        userRepository.save(user2);

        var status1 = Status.builder()
                .id(1l)
                .status("To-do")
                .build();
        statusRepository.save(status1);

        var status2 = Status.builder()
                .id(2l)
                .status("Doing")
                .build();
        statusRepository.save(status2);

        var status3 = Status.builder()
                .id(3l)
                .status("Done")
                .build();
        statusRepository.save(status3);

        var author = Task.builder()
                .name("Dev task")
                .description("Dev and other things")
                .build();
        taskRepository.save(author);

        var author2 = Task.builder()
                .name("Testing task")
                .description("Testing and other things")
                .user(user3)
                .build();
        taskRepository.save(author2);




        taskRepository.findAll().forEach(System.out::println);
    }
}
