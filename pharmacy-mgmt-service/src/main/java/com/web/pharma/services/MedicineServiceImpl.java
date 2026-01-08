package com.web.pharma.services;

import com.web.pharma.dtos.MedicineRequestDto;
import com.web.pharma.dtos.MedicineResponseDto;
import com.web.pharma.models.Medicine;
import com.web.pharma.repos.MedicineRepository;
import com.web.pharma.utils.CommonUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public MedicineResponseDto createMedicine(MedicineRequestDto dto) {
        Medicine medicine = MedicineRequestDto.toEntity(dto);
        medicineRepository.save(medicine);

        return MedicineResponseDto.toDto(medicine);
    }

    @Override
    public List<MedicineResponseDto> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(MedicineResponseDto::toDto)
                .toList();
    }

    @Override
    public MedicineResponseDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow();
        return MedicineResponseDto.toDto(medicine);
    }

    @Override
    public MedicineResponseDto updateMedicine(Long id, MedicineRequestDto dto) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow();
        medicine.setName(dto.name());
        medicine.setBrand(dto.brand());
        medicine.setBatchNumber(dto.batchNumber());
        medicine.setExpiryDate(dto.expiryDate());
        medicine.setUnitPrice(dto.price());
        medicine.setStock(dto.stock());
        medicine.setPrescriptionRequired(dto.prescriptionRequired());
        return MedicineResponseDto.toDto(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void uploadMedicinesFromExcel(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Excel file is empty");
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);
            List<Medicine> medicines = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Medicine medicine = new Medicine();
                medicine.setName(row.getCell(0).getStringCellValue());
                medicine.setBrand(row.getCell(1).getStringCellValue());
                medicine.setBatchNumber(row.getCell(2).getStringCellValue());

                LocalDate expiryDate = row.getCell(3).getLocalDateTimeCellValue().toLocalDate();

                // ✅ Expiry date validation
                if (expiryDate.isBefore(LocalDate.now())) {
                    throw new RuntimeException("Expired medicine at row " + (i + 1));
                }

                medicine.setExpiryDate(expiryDate);
                medicine.setUnitPrice(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
                medicine.setStock((int) row.getCell(5).getNumericCellValue());
                medicine.setPrescriptionRequired(row.getCell(6).getBooleanCellValue());

                medicines.add(medicine);
            }

            medicineRepository.saveAll(medicines);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file", e);
        }
    }

    @Transactional
    public void generateAndSaveValidMedicines() {
        // Predefined valid medicines
        String[] medicineNames = CommonUtils.getMedicineNames();

        String[] brands = {"Cipla", "Pfizer", "Sun Pharma", "Novartis", "GSK", "Aurobindo", "Abbott"};

        List<Medicine> medicineList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Medicine medicine = new Medicine();
            medicine.setName(medicineNames[i % medicineNames.length]);
            medicine.setBrand(brands[i % brands.length]);
            medicine.setBatchNumber("BATCH" + String.format("%04d", i + 1));
            medicine.setExpiryDate(LocalDate.now().plusMonths((i % 24) + 1)); // 1–24 months expiry
            medicine.setUnitPrice(BigDecimal.valueOf(20 + (i % 50) * 1.0)); // price 20–70
            medicine.setStock(10 + (i % 100)); // stock 10–110
            medicine.setPrescriptionRequired(i % 3 == 0); // true for every 3rd medicine

            medicineList.add(medicine);
        }

        // Save all medicines in a batch
        medicineRepository.saveAll(medicineList);
    }

}

