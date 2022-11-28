package com.example.activitytracker.service.impl;

import com.example.activitytracker.entities.Task;
import com.example.activitytracker.entities.User;
import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.model.TaskCategory;
import com.example.activitytracker.repositories.TaskRepository;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.shared.dto.TaskDto;
import com.example.activitytracker.shared.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.AccessException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TaskRepository taskRepository;
    @Mock
    Utils utils;
    @InjectMocks
    TaskServiceImpl taskService;
    TaskDto taskDto;
    Task task;
    Task task2;
    Task task3;
    Task task4;

    List<Task> taskList;

    User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        taskDto = new TaskDto();
        taskDto.setTitle("This is the title");
        taskDto.setDescription("This is the body of the description");


        user = new User();
        user.setId(1L);
        user.setUserId("qWeryS892");
        user.setFirstname("Ikechi");
        user.setLastname("Ucheagwu");
        user.setEmail("ikechi@gmail.com");
        user.setPassword("123456");

        //NB: INCOMPLETE IN DATABASE MEANS PENDING TO USER
        task = new Task();
        task.setId(1L);
        task.setTaskId("ytre546789021qw");
        task.setTitle("This is the title");
        task.setDescription("This is the body of the description");
        task.setStatus(TaskCategory.INCOMPLETE.getStatus());
        task.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        task.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        task.setCompletedAt(null);
        task.setUserDetails(user);

        task2 = new Task();
        task2.setId(2L);
        task2.setTaskId("ytre546789021qb");
        task2.setTitle("This is the title2");
        task2.setDescription("This is the body of the description2");
        task2.setStatus(TaskCategory.INCOMPLETE.getStatus());
        task2.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        task2.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        task2.setCompletedAt(null);
        task2.setUserDetails(user);

        task3 = new Task();
        task3.setId(3L);
        task3.setTaskId("ytre546789021qz");
        task3.setTitle("This is the title3");
        task3.setDescription("This is the body of the description3");
        task3.setStatus(TaskCategory.DONE.getStatus());
        task3.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        task3.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        task3.setCompletedAt(LocalDateTime.now(Clock.systemUTC()));
        task3.setUserDetails(user);

        task4 = new Task();
        task4.setId(4L);
        task4.setTaskId("ytre546789021qv");
        task4.setTitle("This is the title4");
        task4.setDescription("This is the body of the description4");
        task4.setStatus(TaskCategory.IN_PROGRESS.getStatus());
        task4.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        task4.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        task4.setCompletedAt(null);
        task4.setUserDetails(user);

        taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
    }

    @Test
    void createTask() {
        when(userRepository.findByUserId(anyString()))
                .thenReturn(Optional.of(user));
        when(utils.generateTaskId(15))
                .thenReturn("ytre546789021qw");
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskDto dto = taskService.createTask("qWeryS892", taskDto);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    void createTask_Fail_Wrong_UserId() {
        when(userRepository.findByUserId(anyString()))
                .thenThrow(new ActivityTrackerException(ErrorMessages.WRONG_USER_ID.getErrorMessage()));
        ActivityTrackerException thrown =
                assertThrows(ActivityTrackerException.class, () -> taskService.createTask("jtqyuikj", taskDto));
        assertEquals(ErrorMessages.WRONG_USER_ID.getErrorMessage(), thrown.getMessage());
        verify(userRepository, times(1))
                .findByUserId(anyString());
    }

    @Test
    void updateTask() {
        when(userRepository.findByUserId(anyString()))
                .thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString()))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskDto dto = taskService.updateTask("qWeryS892", "1q2w3e4r5t6y7u8", taskDto);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    void updateTask_Fail_Wrong_TaskId() {
        when(userRepository.findByUserId(anyString()))
                .thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString()))
                .thenThrow(new ActivityTrackerException(ErrorMessages.WRONG_TASK_ID.getErrorMessage()));
        ActivityTrackerException thrown =
                assertThrows(ActivityTrackerException.class,
                        () -> taskService.updateTask("jtqyuikj", "6514678iuhgdklj", taskDto));
        assertEquals(ErrorMessages.WRONG_TASK_ID.getErrorMessage(), thrown.getMessage());
        verify(userRepository, times(1))
                .findByUserId(anyString());
    }

    @Test
    void getTask() {
        when(userRepository.findByUserId(anyString()))
                .thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString()))
                .thenReturn(Optional.of(task));
        TaskDto dto = taskService.getTask("qWeryS892", "ytre546789021qw");
        assertNotNull(dto);
        assertEquals(task.getTaskId(), dto.getTaskId());
        assertEquals(task.getTitle(), dto.getTitle());
    }

    @Test
    void getAllTasks() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUserDetails(any(User.class))).thenReturn(taskList);

        List<TaskDto> taskDtos = taskService.getAllTasks("qWeryS892");

        assertEquals(4, taskDtos.size());
        assertEquals(1L, taskDtos.get(0).getId());
        assertEquals(2L, taskDtos.get(1).getId());
    }

    @Test
    @DisplayName("Get all task in pending state")
    void getAllTasksByStatus_Pending() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        //NB: INCOMPLETE IN DATABASE MEANS PENDING TO USER
        when(taskRepository.findAllByUserDetailsAndStatus(any(User.class), eq(TaskCategory.INCOMPLETE.getStatus())))
                .thenReturn(getTaskList(TaskCategory.INCOMPLETE.getStatus()));

        List<TaskDto> taskDtos = taskService.getAllTasksByStatus("qWeryS892", TaskCategory.PENDING.getStatus());

        assertEquals(2, taskDtos.size());
    }

    @Test
    @DisplayName("Get all task in progress state")
    void getAllTasksByStatus_Progress() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUserDetailsAndStatus(any(User.class), eq(TaskCategory.IN_PROGRESS.getStatus())))
                .thenReturn(getTaskList(TaskCategory.IN_PROGRESS.getStatus()));

        List<TaskDto> taskDtos = taskService.getAllTasksByStatus("qWeryS892", TaskCategory.IN_PROGRESS.getStatus());

        assertEquals(1, taskDtos.size());
    }

    @Test
    @DisplayName("Get all task in done state")
    void getAllTasksByStatus_Done() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUserDetailsAndStatus(any(User.class), eq(TaskCategory.DONE.getStatus())))
                .thenReturn(getTaskList(TaskCategory.DONE.getStatus()));

        List<TaskDto> taskDtos = taskService.getAllTasksByStatus("qWeryS892", TaskCategory.DONE.getStatus());

        assertEquals(1, taskDtos.size());
        assertNotNull(taskDtos.get(0).getCompletedAt());
    }

    @Test
    void changeStatus() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString())).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        TaskDto dto = taskService.changeStatus("qWeryS892", "ytre546789021qw", TaskCategory.DONE.getStatus());

        assertEquals(dto.getStatus(), TaskCategory.DONE.getStatus());
        assertNotNull(dto);
    }

    @Test
    void changeStatus_Fail_Already_In_Pending_State() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString())).thenReturn(Optional.of(task));

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                () -> taskService.changeStatus("qWeryS892", "ytre546789021qw", TaskCategory.PENDING.getStatus()));

        assertEquals(thrown.getMessage(), ErrorMessages.ALREADY_IN_PENDING_STATE.getErrorMessage());
    }

    @Test
    void changeStatus_Fail_Already_In_Progress_State() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString())).thenReturn(Optional.of(task4));

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                () -> taskService.changeStatus("qWeryS892", "ytre546789021qw", TaskCategory.IN_PROGRESS.getStatus()));

        assertEquals(thrown.getMessage(), ErrorMessages.ALREADY_IN_PROGRESS_STATE.getErrorMessage());
    }

    @Test
    void changeStatus_Fail_Already_In_Done_State() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString())).thenReturn(Optional.of(task3));

        ActivityTrackerException thrown = assertThrows(ActivityTrackerException.class,
                () -> taskService.changeStatus("qWeryS892", "ytre546789021qw", TaskCategory.DONE.getStatus()));

        assertEquals(thrown.getMessage(), ErrorMessages.ALREADY_IN_DONE_STATE.getErrorMessage());
    }

    @Test
    void deleteTask() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId(anyString())).thenReturn(Optional.of(task));
        TaskDto dto = taskService.deleteTask("qWeryS892", "ytre546789021qw");
        assertNull(dto);
    }

    private List<Task> getTaskList(String searchStatus) {
        return taskList.stream()
                .filter(aTask -> aTask.getStatus().equals(searchStatus))
                .toList();
    }

}