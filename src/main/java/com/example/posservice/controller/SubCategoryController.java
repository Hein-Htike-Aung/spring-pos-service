package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.SubCategoryDto;
import com.example.posservice.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @GetMapping("/list")
    public ResponseEntity<List<SubCategoryDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.subCategoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDto> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.subCategoryService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody SubCategoryDto subCategoryDto
    ) {
        this.subCategoryService.save(subCategoryDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create/list")
    public ResponseEntity<ApiResponse> createList(
            @RequestBody Collection<SubCategoryDto> subCategoryDtoCollection
    ) {
        this.subCategoryService.saveList(subCategoryDtoCollection);
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
            @RequestBody SubCategoryDto subCategoryDto
    ) {
        subCategoryDto.setId(id);
        this.subCategoryService.save(subCategoryDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully updated")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/by-category/{id}")
    public ResponseEntity<List<SubCategoryDto>> findAllSubCategoriesByCategoryId(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.subCategoryService.findAllSubCategoriesByCategoryId(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long id
    ) {
        this.subCategoryService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully deleted")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }
}
