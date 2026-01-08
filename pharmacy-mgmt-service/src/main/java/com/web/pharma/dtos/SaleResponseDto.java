package com.web.pharma.dtos;

import com.web.pharma.models.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDto(
        Long saleId,
        BigDecimal subtotal,
        BigDecimal gst,
        BigDecimal grandTotal,
        String paymentMethod,
        LocalDateTime saleDate,
        List<SaleMedicineDto> itemDtos
) {
    /**
     * Converts a Sale entity to SaleResponseDto
     */
    public static SaleResponseDto fromEntity(Sale sale) {
        List<SaleMedicineDto> itemDtos = sale.getSaleMedicines().stream()
                .map(SaleMedicineDto::toEntity) // Each SaleMedicine converts itself
                .toList();

        return new SaleResponseDto(
                sale.getId(),
                sale.getSubtotal(),
                sale.getGst(),
                sale.getGrandTotal(),
                sale.getPaymentMethod(),
                sale.getSaleDate(),
                itemDtos
        );
    }
}

