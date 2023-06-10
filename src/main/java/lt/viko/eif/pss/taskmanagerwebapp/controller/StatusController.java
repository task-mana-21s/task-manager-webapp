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

@RestController
@RequestMapping("/api")
@CrossOrigin
class StatusController {

    private final Logger log = LoggerFactory.getLogger(StatusController.class);
    private StatusRepository statusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

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

    @GetMapping("/statuses/{id}")
    EntityModel<Status> getStatus(@PathVariable Long id) {
        Status status = statusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(status, WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).getStatus(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(StatusController.class).statuses()).withRel("statuses"));
    }

    @PostMapping("/statuses")
    ResponseEntity<Status> createStatus(@Valid @RequestBody Status status) throws URISyntaxException {
        log.info("Request to create Status: {}", status);
        Status result = statusRepository.save(status);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getId()))
                .body(result);
    }

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