package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Employee;
import com.example.posservice.repo.EmployeeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeRepoProvider {
    private final EmployeeRepo employeeRepo;

    public Employee findEmployeeByUserId(Long userId) {
        return this.employeeRepo.findByUserId(userId)
                .orElseThrow(() -> new SpringPosException("No Employee Found with userId - " + userId));
    }

    public Employee findById(Long id) {
        return this.employeeRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Employee found with id - " + id));
    }
}
