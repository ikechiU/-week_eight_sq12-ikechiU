package com.example.activitytracker.service.impl;

import com.example.activitytracker.entities.User;
import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.service.UserService;
import com.example.activitytracker.shared.dto.UserDto;
import com.example.activitytracker.shared.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;


@Service
@AllArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public UserDto createUser(@Valid UserDto userDto) {
        ModelMapper mapper = new ModelMapper();

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent())
            throw new ActivityTrackerException(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());

        User userToCreate = mapper.map(userDto, User.class);
        userToCreate.setUserId(utils.generateUserId(10));

        User createdUser = userRepository.save(userToCreate);
        return mapper.map(createdUser, UserDto.class);
    }
}
