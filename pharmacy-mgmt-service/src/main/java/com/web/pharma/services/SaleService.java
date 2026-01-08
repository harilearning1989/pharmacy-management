package com.web.pharma.services;

import com.web.pharma.dtos.SaleRequestDto;
import com.web.pharma.dtos.SaleResponseDto;
import com.web.pharma.models.Sale;

import java.util.List;

public interface SaleService {
    List<SaleResponseDto> getAllSales();
    Sale getSaleById(Long id);
    void deleteSale(Long id);
    SaleResponseDto createSaleMedicine(SaleRequestDto dto, String username);
}

