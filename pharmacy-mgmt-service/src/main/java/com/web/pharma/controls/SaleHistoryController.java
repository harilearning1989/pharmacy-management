package com.web.pharma.controls;

import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.services.SaleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sales/history")
@RequiredArgsConstructor
public class SaleHistoryController {

    private final SaleHistoryService service;

    @GetMapping("/today")
    public List<SaleHistoryDto> today() {
        return service.getTodaySales();
    }

    @GetMapping("/weekly")
    public List<SaleHistoryDto> weekly() {
        return service.getWeeklySales();
    }

    @GetMapping("/monthly")
    public List<SaleHistoryDto> monthly() {
        return service.getMonthlySales();
    }

    //GET /sales/history/range?startDate=2025-01-01&endDate=2025-01-31
    @GetMapping("/range")
    public List<SaleHistoryDto> dateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return service.getSalesByDateRange(startDate, endDate);
    }

    @GetMapping("/customer/{customerId}")
    public List<SaleHistoryDto> customerSales(
            @PathVariable Long customerId
    ) {
        return service.getSalesByCustomer(customerId);
    }
}

