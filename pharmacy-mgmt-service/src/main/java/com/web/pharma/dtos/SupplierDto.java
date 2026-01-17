package com.web.pharma.dtos;

import com.web.pharma.models.Supplier;

import java.time.LocalDateTime;

public record SupplierDto(
        Long id,
        String supplierCode,
        String supplierName,
        String contactPerson,
        String phone,
        String email,
        String gstNumber,
        String drugLicenseNumber,
        String bankName,
        String bankAccountNumber,
        String ifscCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String status
) {
    public Supplier toNewEntity() {
        return Supplier.builder()
                .supplierCode(supplierCode)
                .supplierName(supplierName)
                .contactPerson(contactPerson)
                .phone(phone)
                .email(email)
                .gstNumber(gstNumber)
                .drugLicenseNumber(drugLicenseNumber)
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .ifscCode(ifscCode)
                .status("Active")
                .build();
    }

    public void updateEntity(Supplier supplier) {
        supplier.setSupplierName(supplierName);
        supplier.setContactPerson(contactPerson);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setGstNumber(gstNumber);
        supplier.setDrugLicenseNumber(drugLicenseNumber);
        supplier.setBankName(bankName);
        supplier.setBankAccountNumber(bankAccountNumber);
        supplier.setIfscCode(ifscCode);
    }

    public static SupplierDto fromEntity(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getSupplierCode(),
                supplier.getSupplierName(),
                supplier.getContactPerson(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getGstNumber(),
                supplier.getDrugLicenseNumber(),
                supplier.getBankName(),
                supplier.getBankAccountNumber(),
                supplier.getIfscCode(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                supplier.getStatus()
        );
    }
}

