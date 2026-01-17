package com.web.pharma.services;

import com.web.pharma.dtos.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto createSupplier(SupplierDto requestDto);

    SupplierDto getSupplierById(Long id);

    List<SupplierDto> getAllSuppliers();

    SupplierDto updateSupplier(Long id, SupplierDto requestDto);

    void deleteSupplier(Long id);
}

