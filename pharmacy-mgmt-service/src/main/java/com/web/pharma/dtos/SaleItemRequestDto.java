package com.web.pharma.dtos;

public record SaleItemRequestDto(
        Long medicineId,
        int quantity
) {
}