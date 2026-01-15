package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDetailDto;
import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.dtos.SalesSummaryResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleHistoryService {

    List<SaleHistoryDto> getTodaySales();

    List<SaleHistoryDto> getWeeklySales();

    List<SaleHistoryDto> getMonthlySales();

    List<SaleHistoryDto> getSalesByDateRange(LocalDate start, LocalDate end);

    List<SaleHistoryDto> getSalesByCustomer(Long customerId);

    List<SaleHistoryDto> getAllSales();

    SaleHistoryDetailDto getSaleById(Long saleId);

    Long getTotalSalesCount();

    SalesSummaryResponseDto getTodaySalesSummary();

    SalesSummaryResponseDto getThisWeekSalesSummary();

    SalesSummaryResponseDto getThisMonthSalesSummary();

    SalesSummaryResponseDto getSalesSummaryByDateRange(
            LocalDateTime start,
            LocalDateTime end
    );


}

