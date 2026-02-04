package com.web.pharma.dtos;

import com.web.pharma.models.SaleMedicine;

import java.math.BigDecimal;

public record SaleMedicineHistoryDto(
        Long id,
        Integer quantity,
        BigDecimal price,
        BigDecimal total,
        MedicineResponseDto medicineResponseDto
) {
    public static SaleMedicineHistoryDto fromEntity(SaleMedicine saleMedicine) {
        return new SaleMedicineHistoryDto(
                saleMedicine.getId(),
                saleMedicine.getQuantity(),
                saleMedicine.getPrice(),
                saleMedicine.getTotal(),
                MedicineResponseDto.toDto(saleMedicine.getMedicine()));
    }
}
