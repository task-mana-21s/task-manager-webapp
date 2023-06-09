package lt.viko.eif.pss.taskmanagerwebapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import lt.viko.eif.pss.taskmanagerwebapp.repository.StatusRepository;
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

 Controller class for managing status resources.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StatusController {

    private final Logger log = LoggerFactory.getLogger(StatusController.class);
    private final StatusRepository statusRepository;

    /**

     Constructs a new StatusController with the given status repository.
     @param statusRepository The status repository to be used.
     */
    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**

     Retrieves all statuses.

     @return The collection of all statuses.
     */
    @GetMapping("/status")
    CollectionModel<EntityModel<Status>> getStatus() {
        List<EntityModel<Status>> statuses = statusRepository.findAll().stream()
                .map(status -> EntityModel.of(status,
                        WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus(status.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(statuses,
                WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus()).withSelfRel());
    }

    /**

     Retrieves a status by its id.
     @param id The id of the status to retrieve.
     @return The status with the specified id.
     */
    @Operation(summary = "Get a status by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the status",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Status.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Status not found",
                    content = @Content)})
    @GetMapping("/status/{id}")
    EntityModel<Status> getStatus(@PathVariable Long id) {
        Status status = statusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(status, WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus()).withRel("status"));
    }

    /**

     Creates a new status.
     @param status The status to create.
     @return The response entity containing the created status.
     @throws URISyntaxException if the URI syntax is invalid.
     */
    @PostMapping("/status")
    ResponseEntity<Status> createStatus(@Valid @RequestBody Status status) throws URISyntaxException {
        log.info("Request to create Status: {}", status);

        Status result = statusRepository.save(status);
        return ResponseEntity.created(new URI("/api/status/" + result.getId()))
                .body(result);
    }

    /**

     Updates an existing status.
     @param status The status to update.
     @return The response entity containing the updated status.
     */
    @PutMapping("/status/{id}")
    ResponseEntity<Status> updateStatus(@Valid @RequestBody Status status) {
        log.info("Request to update Status: {}", status);
        Status result = statusRepository.save(status);
        return ResponseEntity.ok().body(result);
    }

    /**

     Deletes a status by its id.
     @param id The id of the status to delete.
     @return The response entity indicating the success of the deletion.
     */
    @DeleteMapping("/status/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        log.info("Request to delete Status: {}", id);
        statusRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
