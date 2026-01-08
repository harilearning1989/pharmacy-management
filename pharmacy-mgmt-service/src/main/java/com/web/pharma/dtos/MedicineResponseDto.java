package com.web.pharma.dtos;

import com.web.pharma.models.Medicine;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineResponseDto(
        Long id,
        String name,
        String brand,
        String batchNumber,
        LocalDate expiryDate,
        BigDecimal price,
        int stock,
        boolean prescriptionRequired
) {
    public static MedicineResponseDto toDto(Medicine medicine) {
        return new MedicineResponseDto(
                medicine.getId(),
                medicine.getName(),
                medicine.getBrand(),
                medicine.getBatchNumber(),
                medicine.getExpiryDate(),
                medicine.getUnitPrice(),
                medicine.getStock(),
                medicine.isPrescriptionRequired()
        );
    }
}

