package com.example.activitytracker.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRest {
    private String taskId;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
