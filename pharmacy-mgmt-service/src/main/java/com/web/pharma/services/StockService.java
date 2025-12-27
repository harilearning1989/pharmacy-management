package com.web.pharma.services;

import com.web.pharma.models.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    List<Stock> getAllStock();

    Optional<Stock> getStockById(Long id);

    Stock saveStock(Stock stock);

    void deleteStock(Long id);
}
