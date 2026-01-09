package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface SaleHistoryService {

    List<SaleHistoryDto> getTodaySales();
    List<SaleHistoryDto> getWeeklySales();
    List<SaleHistoryDto> getMonthlySales();
    List<SaleHistoryDto> getSalesByDateRange(LocalDate start, LocalDate end);
    List<SaleHistoryDto> getSalesByCustomer(Long customerId);
    List<SaleHistoryDto> getAllSales();

}

