package com.web.pharma.services;


import com.web.pharma.dtos.SaleItemRequestDto;
import com.web.pharma.dtos.SaleRequestDto;
import com.web.pharma.dtos.SaleResponseDto;
import com.web.pharma.models.*;
import com.web.pharma.repos.CustomerRepository;
import com.web.pharma.repos.MedicineRepository;
import com.web.pharma.repos.SaleRepository;
import com.web.pharma.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public SaleResponseDto createSale(SaleRequestDto dto, String username) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSoldBy(user);
        sale.setPaymentMethod(dto.paymentMethod());
        sale.setSaleDate(LocalDateTime.now());

        List<SaleItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleItemRequestDto itemDto : dto.items()) {

            Medicine medicine = medicineRepository.findById(itemDto.medicineId())
                    .orElseThrow();

            if (medicine.getStock() < itemDto.quantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            medicine.setStock(medicine.getStock() - itemDto.quantity());

            BigDecimal itemTotal =
                    medicine.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity()));

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setMedicine(medicine);
            item.setQuantity(itemDto.quantity());
            item.setUnitPrice(medicine.getPrice());
            item.setTotalPrice(itemTotal);

            totalAmount = totalAmount.add(itemTotal);
            items.add(item);
        }

        sale.setTotalAmount(totalAmount);
        sale.setItems(items);

        saleRepository.save(sale);

        return new SaleResponseDto(
                sale.getId(),
                customer.getName(),
                totalAmount,
                sale.getSaleDate()
        );
    }

    @Override
    public List<SaleResponseDto> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(s -> new SaleResponseDto(
                        s.getId(),
                        s.getCustomer().getName(),
                        s.getTotalAmount(),
                        s.getSaleDate()
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

