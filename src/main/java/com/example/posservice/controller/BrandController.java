package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.dto.BrandDto;
import com.example.posservice.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/list")
    public ResponseEntity<List<BrandDto>> getAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.brandService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.brandService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody BrandDto brandDto
    ) {
        this.brandService.save(brandDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create/list")
    public ResponseEntity<ApiResponse> createList(
            @RequestBody Collection<BrandDto> brandDtoCollection
    ) {
        this.brandService.saveList(brandDtoCollection);
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
            @RequestBody BrandDto brandDto
    ) {
        brandDto.setId(id);
        this.brandService.save(brandDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/by-category/{id}")
    public ResponseEntity<List<BrandDto>> getAllBrandsByCategoryId(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.brandService.getByCategoryId(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long id
    ) {
        this.brandService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }
}
