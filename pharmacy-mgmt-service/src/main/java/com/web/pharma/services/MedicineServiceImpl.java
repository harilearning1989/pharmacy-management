package com.web.pharma.services;

import com.web.pharma.dtos.MedicineRequestDto;
import com.web.pharma.dtos.MedicineResponseDto;
import com.web.pharma.models.Medicine;
import com.web.pharma.repos.MedicineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public MedicineResponseDto createMedicine(MedicineRequestDto dto) {
        Medicine medicine = Medicine.builder()
                .name(dto.name())
                .brand(dto.brand())
                .batchNumber(dto.batchNumber())
                .expiryDate(dto.expiryDate())
                .price(dto.price())
                .stock(dto.stock())
                .prescriptionRequired(dto.prescriptionRequired())
                .build();
        medicineRepository.save(medicine);

        return mapToDto(medicine);
    }

    @Override
    public List<MedicineResponseDto> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public MedicineResponseDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow();
        return mapToDto(medicine);
    }

    @Override
    public MedicineResponseDto updateMedicine(Long id, MedicineRequestDto dto) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow();
        medicine.setName(dto.name());
        medicine.setBrand(dto.brand());
        medicine.setBatchNumber(dto.batchNumber());
        medicine.setExpiryDate(dto.expiryDate());
        medicine.setPrice(dto.price());
        medicine.setStock(dto.stock());
        medicine.setPrescriptionRequired(dto.prescriptionRequired());
        return mapToDto(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    private MedicineResponseDto mapToDto(Medicine medicine) {
        return new MedicineResponseDto(
                medicine.getId(),
                medicine.getName(),
                medicine.getBrand(),
                medicine.getBatchNumber(),
                medicine.getExpiryDate(),
                medicine.getPrice(),
                medicine.getStock(),
                medicine.isPrescriptionRequired()
        );
    }
}

