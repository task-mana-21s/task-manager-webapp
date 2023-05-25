package lt.viko.eif.pss.taskmanagerwebapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.repository.TaskRepository;
import lt.viko.eif.pss.taskmanagerwebapp.model.Task;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * The type Task controller.
 */
@RestController
@RequestMapping("/api")
class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    private TaskRepository taskRepository;

    /**
     * Instantiates a new Task controller.
     *
     * @param taskRepository the task repository
     */
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieves all Tasks from the Task repository.
     *
     * @return A collection of Task objects.
     */
/*    @GetMapping("/tasks")
    Collection<Task> Tasks() {
        return taskRepository.findAll();
    }*/

    @GetMapping("/tasks")
    CollectionModel<EntityModel<Task>> Tasks() {
        List<EntityModel<Task>> Tasks = taskRepository.findAll().stream()
                .map(Task -> EntityModel.of(Task,
                        WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).getTask(Task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(Tasks,
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).Tasks()).withSelfRel());
    }

/*    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTask(@PathVariable Long id) {
        Optional<Task> Task = taskRepository.findById(id);
        return Task.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    /**
     * Gets task.
     *
     * @param id the id
     * @return the task
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
                WebMvcLinkBuilder.linkTo(methodOn(TaskController.class).Tasks()).withRel("tasks"));
    }

    /**
     * Create task response entity.
     *
     * @param Task the task
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@Valid @RequestBody Task Task) throws URISyntaxException {
        log.info("Request to create Task: {}", Task);
        Task result = taskRepository.save(Task);
        return ResponseEntity.created(new URI("/api/Task/" + result.getId()))
                .body(result);
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
    @DeleteMapping("/Task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("Request to delete Task: {}", id);
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}