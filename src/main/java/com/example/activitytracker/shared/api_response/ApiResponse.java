package com.example.activitytracker.shared.api_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private int httpStatusCode;
    private HttpStatus status;
    private List<Object> message;
    private boolean success;
    private T data;
    private LocalDateTime responseDate;
}
