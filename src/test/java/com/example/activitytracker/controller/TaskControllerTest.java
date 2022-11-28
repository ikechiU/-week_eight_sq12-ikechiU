package com.example.activitytracker.controller;

import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.model.TaskCategory;
import com.example.activitytracker.model.request.TaskRequest;
import com.example.activitytracker.model.response.TaskRest;
import com.example.activitytracker.model.response.UserRest;
import com.example.activitytracker.service.TaskService;
import com.example.activitytracker.service.impl.TaskServiceImpl;
import com.example.activitytracker.shared.api_response.ApiResponse;
import com.example.activitytracker.shared.api_response.ResponseManager;
import com.example.activitytracker.shared.dto.TaskDto;
import com.example.activitytracker.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    TaskServiceImpl taskService;

    @Mock
    ResponseManager<TaskRest> responseManager;

    @Mock
    ResponseManager<List<TaskRest>> manager;

    @InjectMocks
    TaskController taskController;

    private final String userId = "agvdjfbf";
    private final String taskId = "qwedcvbnm9ugdcv";
    private TaskRequest taskRequest;
    private TaskDto taskDto;
    private TaskDto taskDto1;
    private TaskDto taskDto2;
    private TaskDto dto;
    private TaskRest taskRest;

    private List<TaskDto> taskDtos;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        taskRequest = new TaskRequest();
        taskRequest.setTitle("A sample task title");
        taskRequest.setDescription("A sample task description");

        dto = new TaskDto();
        dto.setTitle("A sample task title");
        dto.setDescription("A sample task description");

        taskRest = new TaskRest();
        taskRest.setTaskId("agvdjfbf");
        taskRest.setTitle("A sample task title");
        taskRest.setDescription("A sample task description");
        taskRest.setStatus(TaskCategory.PENDING.getStatus());
        taskRest.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskRest.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskRest.setCompletedAt(null);

        taskDto = new TaskDto();
        taskDto.setTaskId("agvdjfbf");
        taskDto.setTitle("A sample task title");
        taskDto.setDescription("A sample task description");
        taskDto.setStatus(TaskCategory.PENDING.getStatus());
        taskDto.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto.setCompletedAt(null);

        taskDto1 = new TaskDto();
        taskDto1.setTaskId("agvdjfbf");
        taskDto1.setTitle("A sample task title");
        taskDto1.setDescription("A sample task description");
        taskDto1.setStatus(TaskCategory.IN_PROGRESS.getStatus());
        taskDto1.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto1.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto1.setCompletedAt(null);

        taskDto2 = new TaskDto();
        taskDto2.setTaskId("agvdjfbf");
        taskDto2.setTitle("A sample task title");
        taskDto2.setDescription("A sample task description");
        taskDto2.setStatus(TaskCategory.IN_PROGRESS.getStatus());
        taskDto2.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto2.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        taskDto2.setCompletedAt(null);

        taskDtos.add(taskDto);
        taskDtos.add(taskDto1);
        taskDtos.add(taskDto2);
    }

    @Test
    void createTask() {
        when(taskService.createTask(anyString(), any(TaskDto.class))).thenReturn(taskDto);
        when(responseManager.success(eq(HttpStatus.CREATED), any(TaskRest.class)))
                .thenReturn(new ResponseManager<TaskRest>().success(HttpStatus.CREATED, taskRest));

        ApiResponse<TaskRest> response = taskController.createTask(userId, taskRequest);
        assertNotNull(response.getData());
        assertEquals("agvdjfbf", response.getData().getTaskId());
        verify(taskService, times(1)).createTask(userId, dto);
    }

    @Test
    void createTask_Fail() {
        when(taskService.createTask(anyString(), any(TaskDto.class)))
                .thenThrow(new ActivityTrackerException(ErrorMessages.WRONG_USER_ID.getErrorMessage()));

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                ()-> taskController.createTask(userId, taskRequest));

        assertEquals(ErrorMessages.WRONG_USER_ID.getErrorMessage(), thrown.getMessage());
        verify(taskService, times(1)).createTask(userId, dto);
    }

    @Test
    void updateTask() {
        when(taskService.updateTask(anyString(), anyString(), any(TaskDto.class))).thenReturn(taskDto);
        when(responseManager.success(eq(HttpStatus.OK), any(TaskRest.class)))
                .thenReturn(new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest));

        ApiResponse<TaskRest> response = taskController.updateTask(userId, taskId, taskRequest);
        assertNotNull(response.getData());
        verify(taskService, times(1)).updateTask(userId, taskId, dto);
    }

    @Test
    void getTask() {
        when(taskService.getTask(anyString(), anyString())).thenReturn(taskDto);
        when(responseManager.success(eq(HttpStatus.OK), any(TaskRest.class)))
                .thenReturn(new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest));

        ApiResponse<TaskRest> response = taskController.getTask(userId, taskId);
        assertNotNull(response.getData());
        verify(taskService, times(1)).getTask(userId, taskId);
    }

    @Test
    void getAllTasks() {
        when(taskService.getAllTasks(anyString())).thenReturn(taskDtos);
        when(responseManager.success(eq(HttpStatus.OK), any(TaskRest.class)))
                .thenReturn(new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest));

        ApiResponse<TaskRest> response = taskController.getTask(userId, taskId);
        assertNotNull(response.getData());
        verify(taskService, times(1)).getTask(userId, taskId);
    }

    @Test
    void getAllTasksByStatus() {

    }

    @Test
    void changeStatus() {

    }

    @Test
    void deleteTask() {
    }
}