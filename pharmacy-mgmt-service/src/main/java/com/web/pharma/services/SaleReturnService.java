package com.web.pharma.services;

import com.web.pharma.dtos.SaleHistoryDetailDto;

import java.util.List;

public interface SaleReturnService {

    SaleHistoryDetailDto returnSaleMedicines(Long saleId, List<Long> saleMedicineIds);
}
