package com.web.pharma.dtos;

import com.web.pharma.models.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleHistoryDetailDto(
        Long id,
        CustomerDto customer,
        UserDto soldBy,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal gst,
        BigDecimal grandTotal,
        String paymentMethod,
        LocalDateTime saleDate,
        List<SaleMedicineHistoryDto> saleMedicines
) {

    public static SaleHistoryDetailDto fromEntity(Sale sale) {
        return new SaleHistoryDetailDto(
                sale.getId(),
                CustomerDto.toDto(sale.getCustomer()),
                sale.getSoldBy() != null ? UserDto.toDto(sale.getSoldBy()) : null,
                sale.getSubtotal(),
                sale.getDiscount(),
                sale.getGst(),
                sale.getGrandTotal(),
                sale.getPaymentMethod(),
                sale.getSaleDate(),
                sale.getSaleMedicines()
                        .stream()
                        .map(SaleMedicineHistoryDto::fromEntity)
                        .toList()
        );
    }
    public static SaleHistoryDetailDto empty() {
        return new SaleHistoryDetailDto(
                null,
                null,
                null,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                null,
                null,
                List.of()
        );
    }
}

