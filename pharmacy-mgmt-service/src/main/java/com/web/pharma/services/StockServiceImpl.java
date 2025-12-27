package com.web.pharma.services;

import com.web.pharma.models.Stock;
import com.web.pharma.repos.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    @Override
    public List<Stock> getAllStock() {
        return repository.findAll();
    }

    @Override
    public Optional<Stock> getStockById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Stock saveStock(Stock stock) {
        return repository.save(stock);
    }

    @Override
    public void deleteStock(Long id) {
        repository.deleteById(id);
    }
}
