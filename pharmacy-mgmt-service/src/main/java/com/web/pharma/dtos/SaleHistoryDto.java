package com.web.pharma.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleHistoryDto(
        Long saleId,
        String customerName,
        String soldBy,
        BigDecimal totalAmount,
        String paymentMethod,
        LocalDateTime saleDate
) {}

