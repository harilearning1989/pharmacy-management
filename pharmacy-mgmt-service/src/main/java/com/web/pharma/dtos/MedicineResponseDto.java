package com.web.pharma.dtos;

import com.web.pharma.models.Medicine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicineResponseDto(
        Long id,
        String name,
        String brand,
        String batchNumber,
        LocalDate expiryDate,
        Integer dosage,
        BigDecimal price,
        int stock,
        boolean prescriptionRequired,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MedicineResponseDto toDto(Medicine medicine) {
        return new MedicineResponseDto(
                medicine.getId(),
                medicine.getName(),
                medicine.getBrand(),
                medicine.getBatchNumber(),
                medicine.getExpiryDate(),
                medicine.getDosageMg(),
                medicine.getUnitPrice(),
                medicine.getStock(),
                medicine.isPrescriptionRequired(),
                medicine.getCreatedAt(),
                medicine.getUpdatedAt()

        );
    }
}

