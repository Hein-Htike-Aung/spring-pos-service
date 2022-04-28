package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Supplier;
import com.example.posservice.repo.SupplierRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupplierRepoProvider {
    private SupplierRepo supplierRepo;

    public Supplier findSupplierByOrgName(String orgName) {
        return this.supplierRepo.findByOrgName(orgName)
                .orElseThrow(() -> new SpringPosException("No Supplier found with OrgName - " + orgName));
    }

    public Supplier findById(Long id) {
        return this.supplierRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Supplier found with id - " + id));
    }
}
