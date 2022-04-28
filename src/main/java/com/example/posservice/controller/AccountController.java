package com.example.posservice.controller;

import com.example.posservice.dto.*;
import com.example.posservice.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private AppUserService appUserService;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/signup/employee")
    public ResponseEntity<ApiResponse> signUpEmployee(
            @RequestBody EmployeeDto employeeDto
    ) {
        this.appUserService.signUpEmployee(employeeDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Employee Registration Successful")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<ApiResponse> signUpCustomer(
            @RequestBody CustomerDto customerDto
    ) {
        this.appUserService.signUpCustomer(customerDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Customer Registration Successful")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PutMapping("/change-credential")
    public ResponseEntity<ApiResponse> changeCredential(
            @RequestBody ChangeableCredentialInfo changeableCredentialInfo
    ) {
        appUserService.changeCredential(changeableCredentialInfo);
        appUserService.logout();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Successfully updated. Please reactivate your account")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<ApiResponse> verify(
            @PathVariable("token") String token
    ) {
        this.appUserService.activateAccount(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Account Verification Complete")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/block/{username}")
    public ResponseEntity<ApiResponse> blockAccount(
            @PathVariable("username") String username
    ) {
        this.appUserService.blockAccount(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Successfully Blocked Account")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findUserByUsername(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.appUserService.findUserByUsername(username));
    }

    @PostMapping("/login")
    public AuthenticationResponse login(
            @RequestBody LoginRequest loginRequest
    ) {
        return this.appUserService.login(loginRequest);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        appUserService.logout();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("Logout Successfully")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }


}
