package com.web.pharma.controls;

import com.web.pharma.dtos.SaleRequestDto;
import com.web.pharma.dtos.SaleResponseDto;
import com.web.pharma.models.Sale;
import com.web.pharma.models.User;
import com.web.pharma.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleRestController {

    private final SaleService saleService;

    @PostMapping
    public SaleResponseDto createSale(
            @RequestBody SaleRequestDto dto,
            @AuthenticationPrincipal User user
    ) {
        return saleService.createSale(dto, user.getUsername());
    }

    /*{
        "customerId": 1,
            "paymentMethod": "CASH",
            "items": [
        { "medicineId": 10, "quantity": 2 },
        { "medicineId": 12, "quantity": 1 }
  ]
    }*/


    @GetMapping
    public List<SaleResponseDto> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Sale getSale(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }
}

