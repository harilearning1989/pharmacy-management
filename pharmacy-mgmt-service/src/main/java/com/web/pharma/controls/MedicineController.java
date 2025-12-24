package com.web.pharma.controls;

import com.web.pharma.dtos.MedicineRequestDto;
import com.web.pharma.dtos.MedicineResponseDto;
import com.web.pharma.services.MedicineService;
import com.web.pharma.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @Operation(
            summary = "Create a new medicine",
            description = "Creates a new medicine record in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Medicine successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicineResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid medicine data"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public MedicineResponseDto createMedicine(@RequestBody MedicineRequestDto dto) {
        return medicineService.createMedicine(dto);
    }

    @Operation(
            summary = "Get all medicines",
            description = "Retrieves a list of all medicines."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Medicines retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicineResponseDto.class)
                    )
            )
    })
    @GetMapping
    public List<MedicineResponseDto> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @Operation(
            summary = "Get medicine by ID",
            description = "Retrieves a medicine by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Medicine retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicineResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Medicine not found")
    })
    @GetMapping("/{id}")
    public MedicineResponseDto getMedicine(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @Operation(
            summary = "Update a medicine",
            description = "Updates an existing medicine by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Medicine updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicineResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public MedicineResponseDto updateMedicine(@PathVariable Long id,
                                              @RequestBody MedicineRequestDto dto) {
        return medicineService.updateMedicine(id, dto);
    }

    @Operation(
            summary = "Delete a medicine",
            description = "Deletes a medicine by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }

    @PostMapping(value = "/upload-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload medicines via Excel",
            description = "Uploads multiple medicines from an Excel file"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicines uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Excel file")
    })
    public void uploadMedicinesExcel(
            @RequestParam("file") MultipartFile file
    ) {
        medicineService.uploadMedicinesFromExcel(file);
    }

    @GetMapping("/download-valid-sample")
    @Operation(
            summary = "Download sample Excel with 1000 valid medicines",
            description = "Generates a sample Excel file with 1000 realistic medicine entries"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sample Excel generated successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to generate Excel")
    })
    public void downloadValidSampleExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=medicines_valid_sample.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Medicines");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("name");
            header.createCell(1).setCellValue("brand");
            header.createCell(2).setCellValue("batchNumber");
            header.createCell(3).setCellValue("expiryDate"); // YYYY-MM-DD
            header.createCell(4).setCellValue("price");
            header.createCell(5).setCellValue("stock");
            header.createCell(6).setCellValue("prescriptionRequired");

            // Predefined valid medicines
            String[] medicineNames = CommonUtils.getMedicineNames();

            String[] brands = {"Cipla", "Pfizer", "Sun Pharma", "Novartis", "GSK", "Aurobindo", "Abbott"};

            int rowCount = 1;
            for (int i = 0; i < 1000; i++) {
                Row row = sheet.createRow(rowCount++);
                String name = medicineNames[i % medicineNames.length];
                String brand = brands[i % brands.length];

                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(brand);
                row.createCell(2).setCellValue("BATCH" + String.format("%04d", i + 1));
                row.createCell(3).setCellValue(LocalDate.now().plusMonths((i % 24) + 1).toString()); // 1–24 months expiry
                row.createCell(4).setCellValue(20 + (i % 50) * 1.0); // price 20–70
                row.createCell(5).setCellValue(10 + (i % 100)); // stock 10–110
                row.createCell(6).setCellValue(i % 3 == 0); // prescriptionRequired true for every 3rd medicine
            }

            // Auto-size columns
            for (int col = 0; col <= 6; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate valid sample Excel", e);
        }
    }

    @PostMapping("/generate-sample")
    @Operation(
            summary = "Generate and save 1000 valid medicines",
            description = "Generates 1000 realistic medicine entries and saves them directly to the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicines generated and saved successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to generate medicines")
    })
    public ResponseEntity<String> generateSampleMedicines() {
        medicineService.generateAndSaveValidMedicines();
        return ResponseEntity.ok("1000 valid medicines have been saved to the database.");
    }

}

