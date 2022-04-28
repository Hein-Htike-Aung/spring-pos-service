package com.example.posservice.security;

import com.example.posservice.enumeration.Gender;
import com.example.posservice.model.Employee;
import com.example.posservice.model._Authority;
import com.example.posservice.model._User;
import com.example.posservice.repo.AppUserRepo;
import com.example.posservice.repo.AuthorityRepo;
import com.example.posservice.repo.EmployeeRepo;
import com.example.posservice.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Component
@AllArgsConstructor
public class AdminUserInitializer {

    private final AuthorityRepo authorityRepo;
    private final AppUserRepo appUserRepo;
    private final EmployeeRepo employeeRepo;
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        _User user = new _User();
        user.setUsername("AdminUser");
        user.setEmail("admin@gmail.com");
        user.setEnabled(true);
        user.setPassword(this.passwordEncoder.encode("admin"));
        user.setCreated(Instant.now());

        _Authority authority = new _Authority();
        authority.setAuthority("ROLE_ADMIN");
        user.addAuthority(authority);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setPhone("admin");
        employee.setGender(Gender.MALE);
        employee.setDob(Instant.now());
        employee.setAddress("admin");
        employee.setFullName("Admin");
        employee.setMaritalStatus("Admin");

        this.appUserRepo.save(user);
        this.authorityRepo.save(authority);
        this.employeeRepo.save(employee);
    }
}
