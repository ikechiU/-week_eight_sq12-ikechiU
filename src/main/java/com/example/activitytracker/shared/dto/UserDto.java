package com.example.activitytracker.shared.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String userId;
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;
    @NotBlank(message = "Lastname is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private List<TaskDto> taskDtos;
}
