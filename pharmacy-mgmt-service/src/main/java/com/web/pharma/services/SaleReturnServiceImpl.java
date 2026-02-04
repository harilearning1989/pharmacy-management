package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDetailDto;
import com.web.pharma.models.Medicine;
import com.web.pharma.models.Sale;
import com.web.pharma.models.SaleMedicine;
import com.web.pharma.repos.MedicineRepository;
import com.web.pharma.repos.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleReturnServiceImpl implements SaleReturnService {

    private final SaleRepository saleRepository;


    private final MedicineRepository medicineRepository;

    @Override
    public SaleHistoryDetailDto returnSaleMedicines(Long saleId, List<Long> saleMedicineIds) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        Iterator<SaleMedicine> iterator = sale.getSaleMedicines().iterator();

        while (iterator.hasNext()) {
            SaleMedicine sm = iterator.next();

            if (saleMedicineIds.contains(sm.getId())) {
                // 1️⃣ Add returned quantity back to Medicine
                Medicine medicine = sm.getMedicine();
                medicine.setStock(medicine.getStock() + sm.getQuantity());
                medicineRepository.save(medicine);
                // 2️⃣ Remove SaleMedicine from Sale
                iterator.remove(); // orphanRemoval deletes row
            }
        }
        // 3️⃣ If no SaleMedicine left → delete Sale
        if (sale.getSaleMedicines().isEmpty()) {
            saleRepository.delete(sale);
        } else {
            // 4️⃣ Recalculate totals
            recalculateSaleTotals(sale);
            sale = saleRepository.save(sale);
            return SaleHistoryDetailDto.fromEntity(sale);
        }
        return SaleHistoryDetailDto.empty();
    }
    private void recalculateSaleTotals(Sale sale) {
        // Subtotal = sum(unitPrice * quantity)
        BigDecimal subtotal = sale.getSaleMedicines().stream()
                .map(sm -> sm.getPrice()
                        .multiply(BigDecimal.valueOf(sm.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        sale.setSubtotal(subtotal);

        // Discount (flat, already stored)
        BigDecimal discount = sale.getDiscount() != null
                ? sale.getDiscount()
                : BigDecimal.ZERO;

        BigDecimal taxableAmount = subtotal.subtract(discount);
        if (taxableAmount.compareTo(BigDecimal.ZERO) < 0) {
            taxableAmount = BigDecimal.ZERO;
        }

        // GST 18%
        BigDecimal gst = taxableAmount
                .multiply(BigDecimal.valueOf(18))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        sale.setGst(gst);

        // Grand Total
        BigDecimal grandTotal = taxableAmount.add(gst)
                .setScale(2, RoundingMode.HALF_UP);

        sale.setGrandTotal(grandTotal);
    }

}
