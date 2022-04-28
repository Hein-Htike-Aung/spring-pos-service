package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.EmployeeDto;
import com.example.posservice.dto.EmployeeUpdateRequest;
import com.example.posservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.employeeService.getEmployeeDtoById(id));
    }

    @GetMapping("/by-user/{userid}")
    public ResponseEntity<EmployeeDto> getByUserId(
            @PathVariable("userid") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.employeeService.getEmployeeDtoByUserId(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long id,
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) {
        employeeUpdateRequest.setEmployeeId(id);
        this.employeeService.save(employeeUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Successfully updated Employee")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }


}
