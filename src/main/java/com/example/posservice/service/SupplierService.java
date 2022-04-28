package com.example.posservice.service;

import com.example.posservice.dto.SupplierDto;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.SupplierMapper;
import com.example.posservice.model.Purchase;
import com.example.posservice.model.Supplier;
import com.example.posservice.repo.SupplierRepo;
import com.example.posservice.repo.repo_provider.PurchaseRepoProvider;
import com.example.posservice.repo.repo_provider.SupplierRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SupplierService {

    private final SupplierRepo supplierRepo;
    private final SupplierMapper supplierMapper;
    private final SupplierRepoProvider supplierRepoProvider;
    private final PurchaseRepoProvider purchaseRepoProvider;

    public List<SupplierDto> getAll() {
        return this.supplierRepo.findAll().stream()
                .map(this.supplierMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public SupplierDto getSupplierDtoById(Long id) {
        return this.supplierMapper.mapToDto(getSupplierById(id));
    }

    public Supplier getSupplierById(Long id) {
        return this.supplierRepoProvider.findById(id);
    }

    public void save(SupplierDto supplierDto) {

        if (!isExisted(supplierDto.getOrgName(), supplierDto.getFullName())) {
            this.supplierRepo.save(this.supplierMapper.mapToSupplier(supplierDto));
        } else {
            throw new SpringPosException("Already Existed");
        }
    }

    public void update(SupplierDto supplierDto) {
        this.supplierRepo.save(this.supplierMapper.mapToSupplier(supplierDto));
    }

    private boolean isExisted(String orgName, String fullName) {
        return this.supplierRepo.findByOrgName(orgName).isPresent() ||
                this.supplierRepo.findByFullName(fullName).isPresent();
    }

    public void delete(Long supplierId) {
        if (isDeletable(supplierId)) {
            this.supplierRepo.deleteById(supplierId);
        } else {
            throw new SpringPosException("This supplier cannot be deleted");
        }
    }

    private boolean isDeletable(Long supplierId) {
        List<Purchase> purchaseBySupplierId =
                this.purchaseRepoProvider.findPurchaseBySupplierId(supplierId);

        return purchaseBySupplierId.isEmpty();
    }

}
