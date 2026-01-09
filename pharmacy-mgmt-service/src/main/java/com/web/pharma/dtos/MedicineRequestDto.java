package com.web.pharma.dtos;

import com.web.pharma.models.Medicine;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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

    public void applyTo(Medicine medicine) {
        if (StringUtils.hasText(name)) {
            medicine.setName(name);
        }
        if (StringUtils.hasText(brand)) {
            medicine.setBrand(brand);
        }
        if (StringUtils.hasText(batchNumber)) {
            medicine.setBatchNumber(batchNumber);
        }
        if (expiryDate != null) {
            medicine.setExpiryDate(expiryDate);
        }
        if (price != null) {
            medicine.setUnitPrice(price);
        }
        if (stock >=0 && medicine.getStock() != stock) {
            medicine.setStock(stock);
        }
        medicine.setPrescriptionRequired(prescriptionRequired);
    }

}

