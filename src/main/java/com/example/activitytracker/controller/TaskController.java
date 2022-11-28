package com.example.activitytracker.controller;

import com.example.activitytracker.model.request.TaskRequest;
import com.example.activitytracker.model.response.TaskRest;
import com.example.activitytracker.service.TaskService;
import com.example.activitytracker.shared.api_response.ApiResponse;
import com.example.activitytracker.shared.api_response.ResponseManager;
import com.example.activitytracker.shared.dto.TaskDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class TaskController {

    private final TaskService taskService;

    @PostMapping(path = "/{userId}/tasks")
    public ApiResponse<TaskRest> createTask(@PathVariable String userId, @RequestBody TaskRequest taskRequest) {
        ModelMapper mapper = new ModelMapper();
        TaskDto taskDto = mapper.map(taskRequest, TaskDto.class);

        TaskDto createdTask = taskService.createTask(userId, taskDto);
        TaskRest taskRest = mapper.map(createdTask, TaskRest.class);
        return new ResponseManager<TaskRest>().success(HttpStatus.CREATED, taskRest);
    }

    @PutMapping(path = "/{userId}/tasks/{taskId}")
    public ApiResponse<TaskRest> updateTask(@PathVariable String userId, @PathVariable String taskId,
                                            @RequestBody TaskRequest taskRequest) {
        ModelMapper mapper = new ModelMapper();
        TaskDto taskDto = mapper.map(taskRequest, TaskDto.class);

        TaskDto updatedTask = taskService.updateTask(userId, taskId, taskDto);
        TaskRest taskRest = mapper.map(updatedTask, TaskRest.class);
        return new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest);
    }

    @GetMapping(path = "/{userId}/tasks/{taskId}")
    public ApiResponse<TaskRest> getTask(@PathVariable String userId, @PathVariable String taskId) {
        TaskDto taskDto = taskService.getTask(userId, taskId);
        TaskRest taskRest = new ModelMapper().map(taskDto, TaskRest.class);
        return new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest);
    }

    @GetMapping(path = "/{userId}/tasks")
    public ApiResponse<List<TaskRest>> getAllTasks(@PathVariable String userId) {
        List<TaskDto> taskDtos = taskService.getAllTasks(userId);
        Type restType = new TypeToken<List<TaskRest>>() {
        }.getType();
        List<TaskRest> taskRests = new ModelMapper().map(taskDtos, restType);
        return new ResponseManager<List<TaskRest>>().success(HttpStatus.OK, taskRests);
    }

    @GetMapping(path = "/{userId}/tasks/status/{status}")
    public ApiResponse<List<TaskRest>> getAllTasksByStatus(@PathVariable String userId, @PathVariable String status) {
        List<TaskDto> taskDtos = taskService.getAllTasksByStatus(userId, status);
        Type restType = new TypeToken<List<TaskRest>>() {
        }.getType();
        List<TaskRest> taskRests = new ModelMapper().map(taskDtos, restType);
        return new ResponseManager<List<TaskRest>>().success(HttpStatus.OK, taskRests);
    }

    @GetMapping(path = "/{userId}/tasks/{taskId}/{changeStatus}")
    public ApiResponse<TaskRest> changeStatus(@PathVariable String userId, @PathVariable String taskId,
                                              @PathVariable String changeStatus) {
        TaskDto taskDto = taskService.changeStatus(userId, taskId, changeStatus);
        TaskRest taskRest = new ModelMapper().map(taskDto, TaskRest.class);
        return new ResponseManager<TaskRest>().success(HttpStatus.OK, taskRest);
    }

    @DeleteMapping(path = "/{userId}/tasks/{taskId}")
    public ApiResponse<TaskRest> deleteTask(@PathVariable String userId, @PathVariable String taskId) {
        taskService.deleteTask(userId, taskId);
        return new ResponseManager<TaskRest>().success(HttpStatus.OK, null);
    }
}
