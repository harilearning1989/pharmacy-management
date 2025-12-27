package com.web.pharma.controls;

import com.web.pharma.models.Stock;
import com.web.pharma.services.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockRestController {

    private final StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStock();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStock(@PathVariable Long id) {
        return stockService.getStockById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        return stockService.getStockById(id).map(existing -> {
            existing.setMedicineName(stock.getMedicineName());
            existing.setQuantity(stock.getQuantity());
            existing.setPrice(stock.getPrice());
            existing.setExpiryDate(stock.getExpiryDate());
            return ResponseEntity.ok(stockService.saveStock(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }
}
