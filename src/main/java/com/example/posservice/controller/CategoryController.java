package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.CategoryDto;
import com.example.posservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.categoryService.findDtoById(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody CategoryDto categoryDto
    ) {
        this.categoryService.save(categoryDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully saved")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long id,
            @RequestBody CategoryDto categoryDto
    ) {
        categoryDto.setId(id);
        this.categoryService.save(categoryDto);
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
        this.categoryService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully deleted")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }
}
