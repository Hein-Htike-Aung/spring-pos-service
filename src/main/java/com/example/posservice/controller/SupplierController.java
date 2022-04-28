package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.SupplierDto;
import com.example.posservice.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/list")
    public ResponseEntity<List<SupplierDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.supplierService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.supplierService.getSupplierDtoById(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody SupplierDto supplierDto
    ) {
        this.supplierService.save(supplierDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long id,
            @RequestBody SupplierDto supplierDto
    ) {
        supplierDto.setId(id);
        this.supplierService.update(supplierDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully updated")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long id
    ) {
        this.supplierService.delete(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully deleted")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }
}
