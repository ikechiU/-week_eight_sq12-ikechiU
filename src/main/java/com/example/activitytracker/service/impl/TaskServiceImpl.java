package com.example.activitytracker.service.impl;

import com.example.activitytracker.entities.Task;
import com.example.activitytracker.entities.User;
import com.example.activitytracker.exception.ActivityTrackerException;
import com.example.activitytracker.exception.ErrorMessages;
import com.example.activitytracker.model.TaskCategory;
import com.example.activitytracker.repositories.TaskRepository;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.service.TaskService;
import com.example.activitytracker.shared.dto.TaskDto;
import com.example.activitytracker.shared.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Type;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    private final Clock clock;

    @Override
    public TaskDto createTask(String userId, TaskDto taskDto) {
        ModelMapper mapper = new ModelMapper();

        User user = findUserByUserId(userId);
        Task taskToCreate = mapper.map(taskDto, Task.class);
        taskToCreate.setUserDetails(user);
        taskToCreate.setTaskId(utils.generateTaskId(15));
        taskToCreate.setStatus(TaskCategory.IN_PROGRESS.getStatus());

        Task createdTask = taskRepository.save(taskToCreate);
        return mapper.map(createdTask, TaskDto.class);
    }

    @Override
    public TaskDto updateTask(String userId, String taskId, TaskDto taskDto) {
        ModelMapper mapper = new ModelMapper();

        User user = findUserByUserId(userId);
        Task task = findTaskByTaskId(taskId);

        Task taskToUpdate = mapper.map(taskDto, Task.class);
        taskToUpdate.setUserDetails(user);
        taskToUpdate.setId(task.getId());
        taskToUpdate.setTaskId(taskId);
        taskToUpdate.setStatus(task.getStatus());
        taskToUpdate.setCreatedAt(task.getCreatedAt());

        Task updatedTask = taskRepository.save(taskToUpdate);
        return mapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public TaskDto getTask(String userId, String taskId) {
        findUserByUserId(userId);
        Task task = findTaskByTaskId(taskId);
        return new ModelMapper().map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(String userId) {
        User user = findUserByUserId(userId);
        List<Task> taskList = taskRepository.findAllByUserDetails(user);
        Type dtoType = new TypeToken<List<TaskDto>>() {
        }.getType();
        return new ModelMapper().map(taskList, dtoType);
    }

    @Override
    public List<TaskDto> getAllTasksByStatus(String userId, String status) {
        User user = findUserByUserId(userId);
        checkTaskStatus(status);

        String searchStatus = searchStatus(status);
        List<Task> taskList = taskRepository.findAllByUserDetailsAndStatus(user, searchStatus);
        Type dtoType = new TypeToken<List<TaskDto>>() {
        }.getType();
        return new ModelMapper().map(taskList, dtoType);
    }

    @Override
    public TaskDto changeStatus(String userId, String taskId, String changeStatus) {
        findUserByUserId(userId);
        Task task = findTaskByTaskId(taskId);
        checkStatus(task.getStatus(), changeStatus.toLowerCase());

        String status = searchStatus(changeStatus);
        if (status.equals(TaskCategory.DONE.getStatus()))
            task.setCompletedAt(LocalDateTime.now(clock));
        task.setStatus(status);
        Task updatedTaskStatus = taskRepository.save(task);
        return new ModelMapper().map(updatedTaskStatus, TaskDto.class);
    }

    @Override
    public TaskDto deleteTask(String userId, String taskId) {
        findUserByUserId(userId);
        Task task = findTaskByTaskId(taskId);
        taskRepository.delete(task);
        return null;
    }

    private User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).
                orElseThrow(() -> new ActivityTrackerException(ErrorMessages.WRONG_USER_ID.getErrorMessage()));
    }

    private Task findTaskByTaskId(String taskId) {
        return taskRepository.findByTaskId(taskId).
                orElseThrow(() -> new ActivityTrackerException(ErrorMessages.WRONG_TASK_ID.getErrorMessage()));
    }

    private void checkStatus(String status, String changeStatus) {
        if (status.equals(TaskCategory.IN_PROGRESS.getStatus())) {
            if (status.equals(changeStatus))
                throw new ActivityTrackerException(ErrorMessages.ALREADY_IN_PROGRESS_STATE.getErrorMessage());
        }

        if (status.equals(TaskCategory.PENDING.getStatus())) {
            if (status.equals(changeStatus))
                throw new ActivityTrackerException(ErrorMessages.ALREADY_IN_PENDING_STATE.getErrorMessage());
        }

        if (status.equals(TaskCategory.DONE.getStatus())) {
            if (status.equals(changeStatus))
                throw new ActivityTrackerException(ErrorMessages.ALREADY_IN_DONE_STATE.getErrorMessage());
        }

        checkTaskStatus(changeStatus);
    }

    private void checkTaskStatus(String changeStatus) {
        if (!changeStatus.equals(TaskCategory.IN_PROGRESS.getStatus()) && !changeStatus.equals(TaskCategory.DONE.getStatus())
                && !changeStatus.equals(TaskCategory.PENDING.getStatus())) {
            throw new ActivityTrackerException(ErrorMessages.INCORRECT_STATUS_CHANGE.getErrorMessage());
        }
    }

    private String searchStatus(String status) {
        return  (status.equalsIgnoreCase(TaskCategory.DONE.getStatus())) ?
                TaskCategory.DONE.getStatus() : (status.equalsIgnoreCase(TaskCategory.IN_PROGRESS.getStatus())) ?
                TaskCategory.IN_PROGRESS.getStatus() : TaskCategory.INCOMPLETE.getStatus();
    }


}
