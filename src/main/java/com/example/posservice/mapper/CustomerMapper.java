package com.example.posservice.mapper;

import com.example.posservice.dto.CustomerDto;
import com.example.posservice.enumeration.AppUserRole;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Customer;
import com.example.posservice.model._User;
import com.example.posservice.repo.AppUserRepo;
import com.example.posservice.repo.repo_provider.AppUserRepoProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Builder
@AllArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepoProvider appUserRepoProvider;

    public _User mapToUser(CustomerDto customerDto) {

        if (customerDto == null) {
            return null;
        }

        _User user = new _User();
        user.setUsername(customerDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(customerDto.getPassword()));
        user.setEmail(customerDto.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setAuthorities(user.getUserAuthorities(AppUserRole.CUSTOMER.getAuthorities()));

        return user;
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .email(customer.getUser().getEmail())
                .username(customer.getUser().getUsername())
                .enabled(customer.getUser().isEnabled())
                .build();
    }

    public Customer mapToCustomer(CustomerDto customerDto) {

        _User u = this.appUserRepoProvider.findFirstUserByUsername(customerDto.getUsername());

        // Update Email
        u.setEmail(customerDto.getEmail());

        Customer c = new Customer();
        c.setId(customerDto.getId());
        c.setAddress(customerDto.getAddress());
        c.setPhone(customerDto.getPhone());
        c.setFullName(customerDto.getFullName());
        c.setUser(u);
        return c;
    }
}
