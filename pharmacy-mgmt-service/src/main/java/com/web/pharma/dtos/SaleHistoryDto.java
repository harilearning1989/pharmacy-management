package com.web.pharma.dtos;

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
) {}