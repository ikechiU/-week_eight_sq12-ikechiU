package com.example.activitytracker.controller;

import com.example.activitytracker.model.request.UserRequest;
import com.example.activitytracker.model.response.UserRest;
import com.example.activitytracker.service.UserService;
import com.example.activitytracker.shared.api_response.ApiResponse;
import com.example.activitytracker.shared.api_response.ResponseManager;
import com.example.activitytracker.shared.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Clock;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final ResponseManager<UserRest> responseManager;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<UserRest> createUser(@RequestBody UserRequest userRequest) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(userRequest, UserDto.class);
        UserRest userRest = mapper.map(userService.createUser(userDto), UserRest.class);
        return responseManager.success(HttpStatus.CREATED, userRest);
    }
}
