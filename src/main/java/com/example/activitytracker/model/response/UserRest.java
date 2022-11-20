package com.example.activitytracker.model.response;

import lombok.Data;

import java.util.List;

@Data
public class UserRest {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private List<TaskRest> taskRests;
}
