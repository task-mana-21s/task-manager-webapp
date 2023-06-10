package lt.viko.eif.pss.taskmanagerwebapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.repository.StatusRepository;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * The type Task controller.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    /**
     * Instantiates a new Task Controller.
     *
     * @param taskRepository the task repository
     * @param userRepository the user repository
     * @param statusRepository the status repository
     */
    public TaskController(TaskRepository taskRepository, UserRepository userRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * Retrieves all Tasks from the Task repository.
     *
     * @return A collection of Task objects.
     */

    @GetMapping("/tasks")
    CollectionModel<EntityModel<Task>> getTasks() {
        List<EntityModel<Task>> Tasks = taskRepository.findAll().stream()
                .map(Task -> EntityModel.of(Task,
                        WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTask(Task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(Tasks,
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTasks()).withSelfRel());
    }

    /**
     * Gets a task by its ID.
     *
     * @param id the ID of the task
     * @return The task with the specified ID
     */
    @Operation(summary = "Get a task by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the task",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @GetMapping("/tasks/{id}")
    EntityModel<Task> getTask(@PathVariable Long id) {
        Task Task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(Task, WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTask(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTasks()).withRel("tasks"));
    }

    /**
     * Create task response entity.
     *
     * @param task     the task to create
     * @param username the username of the assigned user (optional)
     * @return The created task
     * @throws URISyntaxException if there is an issue with the URI syntax
     */
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @RequestBody(required=false) String username) throws URISyntaxException {
        log.info("Request to create Task: {}{}", task, username);

        Task result = taskRepository.save(task);
        return ResponseEntity.created(new URI("/api/task/" + result.getId()))
                .body(result);
    }
    /**
     * Assigns a user to a task.
     *
     * @param userId the ID of the user to assign
     * @param id     the ID of the task
     * @return the response entity
     */
    @PostMapping("/tasks/{id}/user/{userId}")
    ResponseEntity<Task> assignUserToTask(@PathVariable long userId, @PathVariable Long id) {
        log.info("Request to assign User: {} to Task: {}", userId,  id);
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(userId ==-1){
            task.setUser(null);
            Task result = taskRepository.save(task);
            return ResponseEntity.ok().body(result);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        task.setUser(user);

        Task result = taskRepository.save(task);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/tasks/{id}/status/{statusId}")
    ResponseEntity<Task> assignStatusToTask(@PathVariable long statusId, @PathVariable Long id) {
        log.info("Request to assign Status: {} to Task: {}", statusId,  id);
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(statusId ==-1){
            task.setStatus(null);
            Task result = taskRepository.save(task);
            return ResponseEntity.ok().body(result);
        }
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        task.setStatus(status);

        Task result = taskRepository.save(task);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Update task response entity.
     *
     * @param Task the task
     * @return the response entity
     */
    @PutMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@Valid @RequestBody Task Task) {
        log.info("Request to update Task: {}", Task);
        Task result = taskRepository.save(Task);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete task response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("Request to delete Task: {}", id);
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}