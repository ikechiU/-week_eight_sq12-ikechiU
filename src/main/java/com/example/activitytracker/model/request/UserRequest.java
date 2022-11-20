package com.example.activitytracker.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
