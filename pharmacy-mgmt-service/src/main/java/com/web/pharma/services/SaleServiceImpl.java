package com.web.pharma.services;


import com.web.pharma.dtos.SaleRequestDto;
import com.web.pharma.dtos.SaleResponseDto;
import com.web.pharma.models.Customer;
import com.web.pharma.models.Sale;
import com.web.pharma.models.User;
import com.web.pharma.repos.CustomerRepository;
import com.web.pharma.repos.MedicineRepository;
import com.web.pharma.repos.SaleRepository;
import com.web.pharma.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final MedicineRepository medicineRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SaleResponseDto createSaleMedicine(SaleRequestDto dto, String username) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Sale sale = saleRepository.save(SaleRequestDto.toEntity(dto, customer, user, medicineRepository));
        return SaleResponseDto.fromEntity(sale);
    }

    @Override
    public List<SaleResponseDto> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(sale -> new SaleResponseDto(
                        sale.getId(),
                        sale.getSubtotal(),
                        sale.getGst(),
                        sale.getGrandTotal(),
                        sale.getPaymentMethod(),
                        sale.getSaleDate(),
                        null
                ))
                .toList();
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

}

