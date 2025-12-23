package com.web.pharma.dtos;

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
) {}

