package com.web.pharma.dtos;

import java.math.BigDecimal;
import java.util.List;

public record SalesSummaryResponseDto(
        long totalSales,
        BigDecimal totalAmount,
        BigDecimal totalDiscount,
        BigDecimal totalGst,
        List<SaleHistoryDto> sales
) {}

