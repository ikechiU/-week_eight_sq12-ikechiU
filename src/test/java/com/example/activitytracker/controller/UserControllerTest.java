package com.example.activitytracker.controller;

import com.example.activitytracker.entities.User;
import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.model.request.UserRequest;
import com.example.activitytracker.model.response.UserRest;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.service.impl.UserServiceImpl;
import com.example.activitytracker.shared.api_response.ApiResponse;
import com.example.activitytracker.shared.api_response.ResponseManager;
import com.example.activitytracker.shared.dto.UserDto;
import com.example.activitytracker.shared.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserServiceImpl userService;

    @Mock
    ResponseManager<UserRest> responseManager;

    @InjectMocks
    UserController userController;
    UserDto userDto;
    UserRest userRest;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setFirstname("Ikechi");
        userDto.setLastname("Ucheagwu");
        userDto.setEmail("ikechi@gmail.com");
        userDto.setPassword("123456");

        userRest = new UserRest();
        userRest.setFirstname("Ikechi");
        userRest.setLastname("Ucheagwu");
        userRest.setEmail("ikechi@gmail.com");
    }


    @Test
    void createUser() {
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        when(responseManager.success(eq(HttpStatus.CREATED), any(UserRest.class)))
                .thenReturn(new ResponseManager<UserRest>().success(HttpStatus.CREATED, userRest));

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("Ikechi");
        userRequest.setLastname("Ucheagwu");
        userRequest.setEmail("ikechi@gmail.com");
        userRequest.setPassword("123456");

        ApiResponse<UserRest> response = userController.createUser(userRequest);
        assertNotNull(response);
        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void createUser_Fail() {
        when(userService.createUser(any(UserDto.class)))
                .thenThrow(new ActivityTrackerException(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage()));

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("Ikechi");
        userRequest.setLastname("Ucheagwu");
        userRequest.setEmail("ikechi@gmail.com");
        userRequest.setPassword("123456");

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                ()-> userController.createUser(userRequest));
        assertEquals(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage(), thrown.getMessage());
        verify(userService, times(1)).createUser(any(UserDto.class));
    }
}