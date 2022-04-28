package com.example.posservice.service;

import com.example.posservice.dto.CustomerDto;
import com.example.posservice.mapper.CustomerMapper;
import com.example.posservice.repo.CustomerRepo;
import com.example.posservice.repo.repo_provider.CustomerRepoProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerRepoProvider customerRepoProvider;
    private final CustomerMapper customerMapper;

    public CustomerDto findByUserId(Long userId) {
        return this.customerMapper.mapToCustomerDto(
                this.customerRepoProvider.findCustomerByUserId(userId));
    }


    public CustomerDto findById(Long id) {
        return this.customerMapper.mapToCustomerDto(
                this.customerRepoProvider.findById(id));
    }

    public void save(CustomerDto customerDto) {
        this.customerRepo.save(this.customerMapper.mapToCustomer(customerDto));
    }

    public List<CustomerDto> getAll() {
        return this.customerRepo.findAll().stream()
                .map(this.customerMapper::mapToCustomerDto)
                .collect(Collectors.toList());

    }

    public CustomerDto findCustomerDtoById(Long id) {
        return this.customerMapper.mapToCustomerDto(
                this.customerRepoProvider.findById(id));
    }
}
