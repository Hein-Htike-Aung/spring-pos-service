package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.CustomerDto;
import com.example.posservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerService.getAll());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<CustomerDto> findByUserId(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long id,
            @RequestBody CustomerDto customerDto
    ) {
        customerDto.setId(id);
        this.customerService.save(customerDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Successfully updated Customer")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }
}
