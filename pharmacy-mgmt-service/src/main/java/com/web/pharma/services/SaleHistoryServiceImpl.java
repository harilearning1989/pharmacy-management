package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDetailDto;
import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.dtos.SalesSummaryResponseDto;
import com.web.pharma.exception.ResourceNotFoundException;
import com.web.pharma.models.Sale;
import com.web.pharma.repos.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleHistoryServiceImpl implements SaleHistoryService {

    private final SaleRepository saleRepository;

    @Override
    public List<SaleHistoryDto> getAllSales() {
        return saleRepository.findAllSalesWithCustomerAndUser();
    }

    @Override
    public SaleHistoryDetailDto getSaleById(Long saleId) {
        Sale sale = saleRepository.findWithDetailsById(saleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sale not found with id: " + saleId)
                );

        return SaleHistoryDetailDto.fromEntity(sale);
    }

    @Override
    public List<SaleHistoryDto> getTodaySales() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        return map(saleRepository.findSalesBetween(start, end));
    }

    @Override
    public List<SaleHistoryDto> getWeeklySales() {
        LocalDateTime start = LocalDate.now().minusDays(6).atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        return map(saleRepository.findSalesBetween(start, end));
    }

    @Override
    public List<SaleHistoryDto> getMonthlySales() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        return map(saleRepository.findSalesBetween(start, end));
    }

    @Override
    public List<SaleHistoryDto> getSalesByDateRange(LocalDate start, LocalDate end) {
        return map(
                saleRepository.findSalesBetween(
                        start.atStartOfDay(),
                        end.atTime(23, 59, 59)
                )
        );
    }

    @Override
    public List<SaleHistoryDto> getSalesByCustomer(Long customerId) {
        return map(saleRepository.findByCustomerId(customerId));
    }

    private List<SaleHistoryDto> map(List<Sale> sales) {
        return new ArrayList<>();
    }

    @Override
    public Long getTotalSalesCount() {
        return saleRepository.count();
    }

    @Override
    public SalesSummaryResponseDto getTodaySalesSummary() {
        LocalDate today = LocalDate.now();
        return getSalesSummaryByDateRange(today.atStartOfDay(), today.atTime(23, 59, 59));
    }

    @Override
    public SalesSummaryResponseDto getThisWeekSalesSummary() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        return getSalesSummaryByDateRange(
                startOfWeek.atStartOfDay(),
                endOfWeek.atTime(23, 59, 59)
        );
    }

    @Override
    public SalesSummaryResponseDto getThisMonthSalesSummary() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        return getSalesSummaryByDateRange(
                startOfMonth.atStartOfDay(),
                endOfMonth.atTime(23, 59, 59)
        );
    }

    @Override
    public SalesSummaryResponseDto getSalesSummaryByDateRange(
            LocalDateTime start,
            LocalDateTime end
    ) {
        List<Sale> sales = saleRepository
                .findBySaleDateBetween(start, end);

        List<SaleHistoryDto> saleDtos = sales.stream()
                .map(SaleHistoryDto::toDto)
                .toList();

        BigDecimal totalAmount = sales.stream()
                .map(Sale::getGrandTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = sales.stream()
                .map(Sale::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGst = sales.stream()
                .map(Sale::getGst)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SalesSummaryResponseDto(
                sales.size(),
                totalAmount,
                totalDiscount,
                totalGst,
                saleDtos
        );
    }

}

