package com.web.pharma.controls;

import com.web.pharma.dtos.MedicineRequestDto;
import com.web.pharma.dtos.MedicineResponseDto;
import com.web.pharma.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public MedicineResponseDto createMedicine(@RequestBody MedicineRequestDto dto) {
        return medicineService.createMedicine(dto);
    }

    @GetMapping
    public List<MedicineResponseDto> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public MedicineResponseDto getMedicine(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @PutMapping("/{id}")
    public MedicineResponseDto updateMedicine(@PathVariable Long id,
                                              @RequestBody MedicineRequestDto dto) {
        return medicineService.updateMedicine(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }
}

