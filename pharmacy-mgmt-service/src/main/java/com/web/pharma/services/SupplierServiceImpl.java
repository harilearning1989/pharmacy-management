package com.web.pharma.services;

import com.web.pharma.dtos.SupplierDto;
import com.web.pharma.models.Supplier;
import com.web.pharma.repos.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierDto createSupplier(SupplierDto dto) {
        if (supplierRepository.existsBySupplierCode(dto.supplierCode())) {
            throw new RuntimeException("Supplier code already exists");
        }

        Supplier saved = supplierRepository.save(dto.toNewEntity());
        return SupplierDto.fromEntity(saved);
    }

    @Override
    public SupplierDto updateSupplier(Long id, SupplierDto dto) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        dto.updateEntity(supplier);
        return SupplierDto.fromEntity(supplierRepository.save(supplier));
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierDto::fromEntity)
                .toList();
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

}

