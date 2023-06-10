package lt.viko.eif.pss.taskmanagerwebapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.util.HashUtil;
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
 * The type User controller
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepository;

    /**
     * Instantiates a new User controller.
     *
     * @param userRepository
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all Users from the UserRepository.
     *
     * @return A collection of User objects.
     */


    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAllUsers() {
        log.info("GET ALL USERS request");
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }


    /**
     * Gets the User by its id
     *
     * @param id the userId
     * @return the user with the specified Id
     */
    @Operation(summary = "Get an User by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return EntityModel.of(user, WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    /**
     * Create user response entity.
     *
     * @param user     the user to create
     * @return The created user
     * @throws URISyntaxException if there is an issue with the URI syntax
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        log.info("Request to register User: {}", user);
        if(userRepository.findByUsername(user.getUsername())!= null){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.setPassword(HashUtil.encryptPassword(user.getPassword()));
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + result.getUserId()))
                .body(result);
    }
    /**
     * signin User response entity.
     *
     * @param user that wants to signin
     * @return the user entity that signed in
     */
    @PostMapping("/login")
    public ResponseEntity<User> singinUser(@Valid @RequestBody User user){
        log.error("Request to login User: {}", user);
        User userToBeChecked = this.userRepository.findByUsername(user.getUsername());
        if(userToBeChecked == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!HashUtil.checkPassword(user.getPassword(),userToBeChecked.getPassword())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<User>(userToBeChecked,HttpStatus.OK);
    }



    /**
     * Update user response entity.
     *
     * @param userId that we want to update as path variable
     * @param user that needs to be updated
     * @return the response entity of the updated user
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int userId,@Valid @RequestBody User user) {
        log.info("Request to update User: {}", user);
        if(userId != user.getUserId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User userTemporary = userRepository.findByUsername(user.getUsername());
        if(userTemporary != null && user.getUserId() != userTemporary.getUserId()){
            throw new ResponseStatusException(HttpStatus.IM_USED);
        }
        User result = userRepository.save(user);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete user response entity.
     *
     * @param id of the user that we want to delete
     * @return the response entity of the deleted user
     */
    @CrossOrigin
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request to delete User: {}", id);
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}