package com.web.pharma.dtos;

import com.web.pharma.models.Medicine;
import io.swagger.v3.oas.annotations.Hidden;

import java.math.BigDecimal;
import java.time.LocalDate;

@Hidden
public record MedicineRequestDto(
        String name,
        String brand,
        String batchNumber,
        LocalDate expiryDate,
        BigDecimal price,
        int stock,
        boolean prescriptionRequired
) {
    public static Medicine toEntity(MedicineRequestDto dto) {
        return Medicine.builder()
                .name(dto.name())
                .brand(dto.brand())
                .batchNumber(dto.batchNumber())
                .expiryDate(dto.expiryDate())
                .unitPrice(dto.price())
                .stock(dto.stock())
                .prescriptionRequired(dto.prescriptionRequired())
                .build();
    }

}

