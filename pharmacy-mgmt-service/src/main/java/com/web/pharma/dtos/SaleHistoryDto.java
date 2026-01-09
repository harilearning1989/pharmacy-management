package com.web.pharma.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleHistoryDto(
        Long saleId,
        String username,
        String customerName,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal gst,
        BigDecimal grandTotal,
        String paymentMethod,
        LocalDate saleDate
) {}

