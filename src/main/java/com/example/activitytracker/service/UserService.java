package com.example.activitytracker.service;

import com.example.activitytracker.shared.dto.UserDto;

import javax.validation.Valid;

public interface UserService {
    UserDto createUser(@Valid UserDto userDto);
}
