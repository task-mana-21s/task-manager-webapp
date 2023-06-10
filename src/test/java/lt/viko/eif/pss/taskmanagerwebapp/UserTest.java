package lt.viko.eif.pss.taskmanagerwebapp;

import static org.assertj.core.api.Assertions.assertThat;

import lt.viko.eif.pss.taskmanagerwebapp.controller.UserController;
import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import lt.viko.eif.pss.taskmanagerwebapp.util.HashUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setup(){
        user  = User.builder()
                .userId(1L)
                .username("Testing User")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
    }

    // JUnit test for create new User method
    @DisplayName("JUnit test for save new User method")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject() throws URISyntaxException {
        given(userRepository.save(user)).willReturn(user);
        System.out.println(userRepository);
        User savedUser = userController.createUser(user).getBody();
        assertThat(savedUser).isNotNull();
        System.out.println(savedUser);
    }


    // JUnit test for getAllUsers method
    @DisplayName("JUnit test for getAllUsers method")
    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList(){

        var user2 = User.builder()
                .username("Testing User2")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        given(userRepository.findAll()).willReturn(List.of(user,user2));
        CollectionModel<EntityModel<User>> userList = userController.getAllUsers();
        assertThat(userList).isNotNull();
        assertThat(userList.getContent().size()).isEqualTo(2);
    }

    // JUnit test for getAllUsers if Empty true  method
    @DisplayName("JUnit test for getAllUsers method (negative scenario)")
    @Test
    public void givenEmptyUsersList_whenGetAllUsers_thenReturnEmptyUsersList(){
        var user2 = User.builder()
                .username("Testing User2")
                .email("test@ff.tt")
                .password(HashUtil.encryptPassword("12345"))
                .build();
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        CollectionModel<EntityModel<User>> userList = userController.getAllUsers();
        assertThat(userList.getContent()).isEmpty();
        assertThat(userList.getContent().size()).isEqualTo(0);
    }

    // JUnit test for getUserById method
    @DisplayName("JUnit test for getUserById method")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserObject(){
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        User savedUser = userController.getUser(user.getUserId()).getContent();
        assertThat(savedUser).isNotNull();

    }

    // JUnit test for updateUser method
    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser(){
        given(userRepository.save(user)).willReturn(user);
        user.setEmail("newemail@gmail.com");
        user.setUsername("new Name");
        User updatedUser = userController.updateUser(Math.toIntExact(user.getUserId()), user).getBody();
        assert updatedUser != null;
        assertThat(updatedUser.getEmail()).isEqualTo("newemail@gmail.com");
        assertThat(updatedUser.getUsername()).isEqualTo("new Name");
    }

    // JUnit test for deleteUser method
    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing(){
        long user_Id = 1L;
        willDoNothing().given(userRepository).deleteById(user_Id);
        userController.deleteUser(user_Id);
        verify(userRepository, times(1)).deleteById(user_Id);
    }
}