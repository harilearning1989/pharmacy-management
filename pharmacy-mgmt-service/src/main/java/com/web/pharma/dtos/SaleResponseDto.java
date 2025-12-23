package com.web.pharma.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleResponseDto(
        Long saleId,
        String customerName,
        BigDecimal totalAmount,
        LocalDateTime saleDate
) {}

