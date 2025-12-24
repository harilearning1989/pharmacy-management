package com.web.pharma.services;

import com.web.pharma.dtos.MedicineRequestDto;
import com.web.pharma.dtos.MedicineResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MedicineService {
    MedicineResponseDto createMedicine(MedicineRequestDto dto);
    List<MedicineResponseDto> getAllMedicines();
    MedicineResponseDto getMedicineById(Long id);
    MedicineResponseDto updateMedicine(Long id, MedicineRequestDto dto);
    void deleteMedicine(Long id);

    void uploadMedicinesFromExcel(MultipartFile file);

    void generateAndSaveValidMedicines();
}

