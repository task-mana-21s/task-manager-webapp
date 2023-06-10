package lt.viko.eif.pss.taskmanagerwebapp.controller;

import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.repository.StatusRepository;
import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
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
 * The StatusController class provides RESTful endpoints for managing status entities.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
class StatusController {

    private final Logger log = LoggerFactory.getLogger(StatusController.class);
    private StatusRepository statusRepository;

    /**
     * Constructs a new StatusController with the given StatusRepository.
     *
     * @param statusRepository the status repository
     */
    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Retrieves all statuses.
     *
     * @return a CollectionModel containing EntityModel representations of the statuses,
     *         with self and statuses links
     */
    @GetMapping("/status")
    CollectionModel<EntityModel<Status>> statuses() {
        log.info("GET ALL STATUS request");
        List<EntityModel<Status>> statuses = statusRepository.findAll().stream()
                .map(status -> EntityModel.of(status,
                        WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus(status.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(statuses,
                WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).statuses()).withSelfRel());
    }

    /**
     * Retrieves a specific status by its ID.
     *
     * @param id the ID of the status to retrieve
     * @return the EntityModel representation of the status, with self and statuses links
     * @throws ResponseStatusException if the status with the given ID is not found
     */
    @GetMapping("/statuses/{id}")
    EntityModel<Status> getStatus(@PathVariable Long id) {
        Status status = statusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(status, WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).statuses()).withRel("statuses"));
    }

    /**
     * Creates a new status.
     *
     * @param status the status to create
     * @return a ResponseEntity containing the created status and the location URI
     * @throws URISyntaxException if the URI syntax is invalid
     */
    @PostMapping("/statuses")
    ResponseEntity<Status> createStatus(@Valid @RequestBody Status status) throws URISyntaxException {
        log.info("Request to create Status: {}", status);
        Status result = statusRepository.save(status);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getId()))
                .body(result);
    }

    /**
     * Updates an existing status.
     *
     * @param id     the ID of the status to update
     * @param status the updated status
     * @return a ResponseEntity containing the updated status
     * @throws ResponseStatusException if the status with the given ID is not found
     */
    @PutMapping("/statuses/{id}")
    ResponseEntity<Status> updateStatus(@PathVariable("id") Long id, @Valid @RequestBody Status status) {
        log.info("Request to update Status: {}", status);
        if (!statusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        status.setId(id);
        Status result = statusRepository.save(status);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Deletes a status by its ID.
     *
     * @param id the ID of the status to delete
     * @return a ResponseEntity indicating the success of the operation
     * @throws ResponseStatusException if the status with the given ID is not found
     */
    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        log.info("Request to delete Status: {}", id);
        if (!statusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        statusRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}