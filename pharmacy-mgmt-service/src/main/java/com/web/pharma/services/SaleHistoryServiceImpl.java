package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.models.Sale;
import com.web.pharma.repos.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleHistoryServiceImpl implements SaleHistoryService {

    private final SaleRepository saleRepository;

    @Override
    public List<SaleHistoryDto> getTodaySales() {
        return map(saleRepository.findTodaySales());
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
        return sales.stream()
                .map(s -> new SaleHistoryDto(
                        s.getId(),
                        s.getCustomer().getName(),
                        s.getSoldBy().getUsername(),
                        s.getTotalAmount(),
                        s.getPaymentMethod(),
                        s.getSaleDate()
                ))
                .toList();
    }
}

