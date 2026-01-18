package com.web.pharma.dtos;

import com.web.pharma.models.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleHistoryDto(
        Long saleId,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal gst,
        BigDecimal grandTotal,
        String paymentMethod,
        LocalDateTime saleDate,
        String customerName,
        String customerPhone,
        String username,
        String userPhone
) {
    public static SaleHistoryDto toDto(Sale sale) {
        return new SaleHistoryDto(
                sale.getId(),
                sale.getSubtotal(),
                sale.getDiscount(),
                sale.getGst(),
                sale.getGrandTotal(),
                sale.getPaymentMethod(),
                sale.getSaleDate(),
                sale.getCustomer().getName(),
                sale.getCustomer().getPhone(),
                null,
                null);
    }
}