package com.example.activitytracker.entities;

import com.example.activitytracker.shared.dto.TaskDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "User id is mandatory")
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
