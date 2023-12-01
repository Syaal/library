package com.miniproject.library.controller;

import com.miniproject.library.dto.user.UserRequest;
import com.miniproject.library.dto.user.UserResponse;
import com.miniproject.library.entity.User;
import com.miniproject.library.service.UserService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    ModelMapper mapper;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private User getUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("aku anak dewa coding");
        user.setPassword("admin");
        return user;
    }
    private List<User> userList(){
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("aku dewa coding");
        user2.setPassword("admin");
        return Arrays.asList(getUser(), user2);
    }
    @Test
    void updateUser() {
        User user = getUser();
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("aku anak gacor");
        userRequest.setPassword("admin");
        UserResponse expectedUpdatedUser = mapper.map(user, UserResponse.class);
        when(userService.updateById(1, userRequest)).thenReturn(expectedUpdatedUser);
        ResponseEntity<UserResponse> responseEntity = userController.updateUser(1,userRequest);

        // Asserting the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUpdatedUser, responseEntity.getBody());
    }

    @Test
    void getAllUsers() {
        List<UserResponse> expectedUserResponses = userList().stream().map(user -> mapper.map(user,UserResponse.class)).collect(Collectors.toList());

        when(userService.getAll()).thenReturn(expectedUserResponses);

        ResponseEntity<List<UserResponse>> responseEntity = userController.getAllUsers();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedUserResponses, responseEntity.getBody());
    }

    @Test
    void getUserById() {
        UserResponse expectedUserResponse = new UserResponse();
        when(userService.getById(1)).thenReturn(expectedUserResponse);
        ResponseEntity<UserResponse> responseEntity = userController.getUserById(1);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedUserResponse, responseEntity.getBody());
    }

    @Test
    void deleteUser() {
        User user= getUser();
        doNothing().when(userService).deleteById(getUser().getId());
        ResponseEntity<?> responseEntity = userController.deleteUser(user.getId());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("User deleted successfully", responseEntity.getBody());

        verify(userService, times(1)).deleteById(getUser().getId());
    }
}