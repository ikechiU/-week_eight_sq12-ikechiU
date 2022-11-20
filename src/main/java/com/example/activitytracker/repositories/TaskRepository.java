package com.example.activitytracker.repositories;

import com.example.activitytracker.entities.Task;
import com.example.activitytracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskId(String taskId);
    List<Task> findAllByUserDetails(User userDetails);
    List<Task> findAllByUserDetailsAndStatus(User userDetails, String status);
}
