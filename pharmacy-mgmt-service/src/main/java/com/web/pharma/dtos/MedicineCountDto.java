package com.web.pharma.dtos;

public record MedicineCountDto(
        long totalMedicines,
        long availableMedicines,
        long outOfStockMedicines,
        long expiredMedicines
) {
}
