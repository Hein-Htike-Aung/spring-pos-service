package com.example.posservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private Map<?, ?> data;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}
