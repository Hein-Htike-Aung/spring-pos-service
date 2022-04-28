package com.example.posservice.mapper;

import com.example.posservice.dto.SupplierDto;
import com.example.posservice.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Builder
public class SupplierMapper {

    public SupplierDto mapToDto(Supplier supplier) {
        return SupplierDto.builder()
                .id(supplier.getId())
                .fullName(supplier.getFullName())
                .orgName(supplier.getOrgName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .build();
    }

    public Supplier mapToSupplier(SupplierDto supplierDto) {
        Supplier s = new Supplier();
        s.setId(supplierDto.getId());
        s.setFullName(supplierDto.getFullName());
        s.setPhone(supplierDto.getPhone());
        s.setOrgName(supplierDto.getOrgName());
        s.setAddress(supplierDto.getAddress());
        return s;
    }
}
