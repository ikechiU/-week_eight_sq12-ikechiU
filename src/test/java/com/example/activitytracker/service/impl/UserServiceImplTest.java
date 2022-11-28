package com.example.activitytracker.service.impl;

import com.example.activitytracker.entities.User;
import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.AppExceptionsHandler;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.shared.api_response.ApiResponse;
import com.example.activitytracker.shared.dto.UserDto;
import com.example.activitytracker.shared.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;

    @InjectMocks
    UserServiceImpl userService;

    ApplicationContext context;

    @Autowired
    AppExceptionsHandler handler;

    User user;
    UserDto userDto;

    @BeforeEach
    void init() {
        context = new StaticApplicationContext();
//        AppExceptionsHandler handler = new AppExceptionsHandler(clock);
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUserId("qWeryS892");
        user.setFirstname("Ikechi");
        user.setLastname("Ucheagwu");
        user.setEmail("ikechi@gmail.com");
        user.setPassword("123456");

        userDto = new UserDto();
        userDto.setFirstname("Ikechi");
        userDto.setLastname("Ucheagwu");
        userDto.setEmail("ikechi@gmail.com");
        userDto.setPassword("123456");
    }

    @DisplayName("Successful Creation of User")
    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(utils.generateUserId(8)).thenReturn("qWeryS892");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto dto = userService.createUser(userDto);

        assertNotNull(dto);
        assertEquals(user.getFirstname(), userDto.getFirstname());
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @DisplayName("Unsuccessful Creation of User Email Already Exist")
    @Test
    void createUser_Fail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(utils.generateUserId(8)).thenReturn("qWeryS892");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                () -> userService.createUser(userDto), ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());
        assertEquals(thrown.getMessage(), ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());
    }

    @DisplayName("Unsuccessful Creation of User, Validations Error")
    @Test
    void createUser_Fail_Validations() throws NoSuchMethodException{
//        UserDto pojo = new UserDto();
//        pojo.setFirstname("Ikechi");
////        pojo.setLastname("Ucheagwu");
//        pojo.setEmail("ikechi@gmail.com");
//        pojo.setPassword("123456");
//
////        Validator validator = context.getBean(Validator.class);
//        Validator validator = new LocalValidatorFactoryBean();
//        BindingResult result = new BeanPropertyBindingResult(pojo, "pojo");
//        validator.validate(pojo, result);
//
//        userService.createUser(userDto);


//        MethodParameter methodParameter = new MethodParameter(
//                this.getClass().getMethod("createUser", UserServiceImpl.class), 0);
//        Method method = userService.getClass().getDeclaredMethod("createUser", UserServiceImpl.class);
//
//        MethodArgumentNotValidException bindError = new MethodArgumentNotValidException(
//                new MethodParameter(method, 0), result
//        );
//
//        ApiResponse apiResponse = handler.handleValidationExceptions(bindError);
//
//        assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatus());
//        assertTrue(apiResponse.getMessage().contains("mandatory"));
    }
}