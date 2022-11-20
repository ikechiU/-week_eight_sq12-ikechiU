package com.example.activitytracker.service;

import com.example.activitytracker.shared.dto.TaskDto;

import javax.validation.Valid;
import java.util.List;

public interface TaskService {
    TaskDto createTask(String userId, @Valid TaskDto taskDto);
    TaskDto updateTask(String userId, String taskId, @Valid TaskDto taskDto);
    TaskDto getTask(String userId, String taskId);
    List<TaskDto> getAllTasks(String userId);

    List<TaskDto> getAllTasksByStatus(String userId, String status);
    TaskDto changeStatus(String userId, String taskId, String changeStatus);
    TaskDto deleteTask(String userId, String taskId);
}
