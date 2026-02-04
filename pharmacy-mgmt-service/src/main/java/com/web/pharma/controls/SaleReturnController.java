package com.web.pharma.controls;

import com.web.pharma.dtos.SaleHistoryDetailDto;
import com.web.pharma.services.SaleReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale/return")
@RequiredArgsConstructor
public class SaleReturnController {

    private final SaleReturnService saleReturnService;

    @PutMapping("/{saleId}")
    public SaleHistoryDetailDto updateSale(
            @PathVariable Long saleId,
            @RequestBody List<Long> saleMedicineIds
    ) {

        return saleReturnService.returnSaleMedicines(
                saleId,
                saleMedicineIds
        );
    }
}
