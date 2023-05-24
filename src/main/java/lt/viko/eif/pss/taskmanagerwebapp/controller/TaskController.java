package lt.viko.eif.pss.taskmanagerwebapp.controller;

import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api")
class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieves all Tasks from the Task repository.
     *
     * @return A collection of Task objects.
     */

    @GetMapping("/tasks")
    Collection<Task> Tasks() {
        return taskRepository.findAll();
    }

/*    @GetMapping("/tasks")
    CollectionModel<EntityModel<Task>> Tasks() {
        List<EntityModel<Task>> Tasks = TaskRepository.findAll().stream()
                .map(Task -> EntityModel.of(Task,
                        WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTask(Task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(Tasks,
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).Tasks()).withSelfRel());
    }*/

/*    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTask(@PathVariable Long id) {
        Optional<Task> Task = taskRepository.findById(id);
        return Task.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @GetMapping("/tasks/{id}")
    EntityModel<Task> getTask(@PathVariable Long id) {
        Task Task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(Task, WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTask(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).Tasks()).withRel("tasks"));
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@Valid @RequestBody Task Task) throws URISyntaxException {
        log.info("Request to create Task: {}", Task);
        Task result = taskRepository.save(Task);
        return ResponseEntity.created(new URI("/api/Task/" + result.getId()))
                .body(result);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@Valid @RequestBody Task Task) {
        log.info("Request to update Task: {}", Task);
        Task result = taskRepository.save(Task);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/Task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("Request to delete Task: {}", id);
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}