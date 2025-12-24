package com.web.pharma.dtos;

import java.util.List;

public record SaleRequestDto(
        Long customerId,
        String paymentMethod,
        List<SaleItemRequestDto> items
) {}

