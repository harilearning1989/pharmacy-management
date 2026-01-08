package com.web.pharma.dtos;

import com.web.pharma.models.*;
import com.web.pharma.repos.MedicineRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record SaleRequestDto(
        Long customerId,
        Double subTotal,
        BigDecimal discount,
        Double gst,
        Double grandTotal,
        String paymentMethod,
        List<SaleMedicineDto> items
) {

    public static Sale toEntity(
            SaleRequestDto dto,
            Customer customer,
            User user,
            MedicineRepository medicineRepository
    ) {
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSoldBy(user);
        sale.setPaymentMethod(dto.paymentMethod());

        BigDecimal subtotal = BigDecimal.ZERO;
        List<SaleMedicine> saleMedicines = new ArrayList<>();

        for (SaleMedicineDto itemDto : dto.items()) {
            Medicine medicine = medicineRepository.findById(itemDto.medicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));

            if (medicine.getStock() < itemDto.quantity()) {
                throw new RuntimeException(
                        "Insufficient stock for medicine: " + medicine.getName()
                );
            }

            // Deduct stock
            medicine.setStock(medicine.getStock() - itemDto.quantity());

            // Convert DTO to SaleMedicine entity
            SaleMedicine saleMedicine = itemDto.toEntity(sale, medicine);
            saleMedicines.add(saleMedicine);

            subtotal = subtotal.add(
                    medicine.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.quantity()))
            );
        }

        // Totals
        BigDecimal discount = dto.discount() != null ? dto.discount() : BigDecimal.ZERO;
        BigDecimal gst = subtotal.multiply(new BigDecimal("0.05"));
        BigDecimal grandTotal = subtotal.add(gst).subtract(discount);

        // Assign totals and items
        sale.setSubtotal(subtotal);
        sale.setDiscount(discount);
        sale.setGst(gst);
        sale.setGrandTotal(grandTotal);
        sale.setSaleMedicines(saleMedicines);

        return sale;
    }

}


