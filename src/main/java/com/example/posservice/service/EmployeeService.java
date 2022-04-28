package com.example.posservice.service;

import com.example.posservice.dto.EmployeeDto;
import com.example.posservice.dto.EmployeeUpdateRequest;
import com.example.posservice.mapper.EmployeeMapper;
import com.example.posservice.model.Employee;
import com.example.posservice.repo.EmployeeRepo;
import com.example.posservice.repo.repo_provider.EmployeeRepoProvider;
import com.example.posservice.security.CurrentUserHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final EmployeeRepoProvider employeeRepoProvider;
    private final EmployeeMapper employeeMapper;
    private final CurrentUserHolder currentUserHolder;

    public List<EmployeeDto> getAll() {

        return this.employeeRepo.findAll().stream()
                .filter(this::excludingCurrentUser)
                .map(employeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private boolean excludingCurrentUser(Employee employee) {
        String currentUser_username = currentUserHolder.getCurrentUser().getUsername();
        return !employee.getUser().getUsername().equals(currentUser_username);
    }

    public EmployeeDto getEmployeeDtoById(Long id) {
        return this.employeeMapper.mapToDto(
                this.employeeRepoProvider.findById(id));
    }

    public void save(EmployeeUpdateRequest employeeUpdateRequest) {
        this.employeeRepo.save(employeeMapper.mapToEmployee(employeeUpdateRequest));
    }

    public EmployeeDto getEmployeeDtoByUserId(Long userId) {
        return this.employeeMapper.mapToDto(getEmployeeByUserId(userId));
    }

    public Employee getEmployeeByUserId(Long userId) {
        return this.employeeRepoProvider.findEmployeeByUserId(userId);
    }

}
