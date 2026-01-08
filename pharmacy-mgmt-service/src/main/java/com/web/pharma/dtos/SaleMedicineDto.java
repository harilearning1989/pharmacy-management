package com.web.pharma.dtos;

import com.web.pharma.models.Medicine;
import com.web.pharma.models.Sale;
import com.web.pharma.models.SaleMedicine;

import java.math.BigDecimal;

public record SaleMedicineDto(
        Long medicineId,
        String batchNumber,
        Integer quantity,
        BigDecimal price,
        BigDecimal total
) {

    public SaleMedicine toEntity(Sale sale, Medicine medicine) {
        BigDecimal total = medicine.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
        SaleMedicine saleMedicine = new SaleMedicine();
        saleMedicine.setSale(sale);
        saleMedicine.setMedicine(medicine);
        saleMedicine.setQuantity(quantity);
        saleMedicine.setPrice(medicine.getUnitPrice());
        saleMedicine.setTotal(total);
        saleMedicine.setBatchNumber(batchNumber);
        return saleMedicine;
    }

    /**
     * Converts a SaleMedicine entity to SaleMedicineDto
     */
    public static SaleMedicineDto toEntity(SaleMedicine sm) {
        return new SaleMedicineDto(
                sm.getMedicine().getId(),
                sm.getBatchNumber(),
                sm.getQuantity(),
                sm.getPrice(),
                sm.getTotal()
        );
    }
}