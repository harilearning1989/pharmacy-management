package com.web.pharma.dtos;

import com.web.pharma.models.Supplier;

import java.time.LocalDateTime;
import java.util.function.Consumer;

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
        updateIfChanged(supplierName, supplier.getSupplierName(), supplier::setSupplierName);
        updateIfChanged(contactPerson, supplier.getContactPerson(), supplier::setContactPerson);
        updateIfChanged(phone, supplier.getPhone(), supplier::setPhone);
        updateIfChanged(email, supplier.getEmail(), supplier::setEmail);
        updateIfChanged(gstNumber, supplier.getGstNumber(), supplier::setGstNumber);
        updateIfChanged(drugLicenseNumber, supplier.getDrugLicenseNumber(), supplier::setDrugLicenseNumber);
        updateIfChanged(bankName, supplier.getBankName(), supplier::setBankName);
        updateIfChanged(bankAccountNumber, supplier.getBankAccountNumber(), supplier::setBankAccountNumber);
        updateIfChanged(ifscCode, supplier.getIfscCode(), supplier::setIfscCode);
        updateIfChanged(status, supplier.getStatus(), supplier::setStatus);
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

    private <T> void updateIfChanged(T newValue, T oldValue, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
        }
    }

}

