package com.example.posservice.mapper;

import com.example.posservice.dto.EmployeeDto;
import com.example.posservice.dto.EmployeeUpdateRequest;
import com.example.posservice.enumeration.AppUserRole;
import com.example.posservice.model.Employee;
import com.example.posservice.model._User;
import com.example.posservice.repo.repo_provider.EmployeeRepoProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepoProvider employeeRepoProvider;

    public _User mapToUser(EmployeeDto employeeRegisterRequest) {
        if (employeeRegisterRequest == null) {
            return null;
        }

        _User user = _User.builder()
                .username(employeeRegisterRequest.getUsername())
                .password(this.passwordEncoder.encode(employeeRegisterRequest.getPassword()))
                .created(Instant.now())
                .email(employeeRegisterRequest.getEmail())
                .enabled(false)
                .build();

        user.setAuthorities(user.getUserAuthorities(AppUserRole.EMPLOYEE.getAuthorities()));

        return user;
    }

    public Employee mapToEmployee(EmployeeDto employeeRegisterRequest) {
        Employee e = new Employee();
        e.setDob(employeeRegisterRequest.getDob());
        e.setGender(employeeRegisterRequest.getGender());
        e.setMaritalStatus(employeeRegisterRequest.getMaritalStatus());
        e.setFullName(employeeRegisterRequest.getFullName());
        e.setAddress(employeeRegisterRequest.getAddress());
        e.setPhone(employeeRegisterRequest.getPhone());

        return e;
    }

    public Employee mapToEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        Employee storedEmployee = this.employeeRepoProvider.findById(employeeUpdateRequest.getEmployeeId());

        // Update Email
        storedEmployee.getUser().setEmail(employeeUpdateRequest.getEmail());

        Employee e = new Employee();
        // New Data
        e.setId(employeeUpdateRequest.getEmployeeId());
        e.setMaritalStatus(employeeUpdateRequest.getMaritalStatus());
        e.setAddress(employeeUpdateRequest.getAddress());
        e.setPhone(employeeUpdateRequest.getPhone());
        // Old Data
        e.setDob(storedEmployee.getDob());
        e.setFullName(storedEmployee.getFullName());
        e.setGender(storedEmployee.getGender());
        e.setUser(storedEmployee.getUser());
        return e;
    }

    public EmployeeDto mapToDto(Employee employee) {

        return EmployeeDto.builder()
                .id(employee.getId())
                .address(employee.getAddress())
                .dob(employee.getDob())
                .fullName(employee.getFullName())
                .gender(employee.getGender())
                .phone(employee.getPhone())
                .email(employee.getUser().getEmail())
                .maritalStatus(employee.getMaritalStatus())
                .username(employee.getUser().getUsername())
                .enabled(employee.getUser().isEnabled())
                .build();
    }
}
