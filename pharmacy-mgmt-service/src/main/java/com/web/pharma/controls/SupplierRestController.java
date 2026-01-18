package com.web.pharma.controls;

import com.web.pharma.dtos.SupplierDto;
import com.web.pharma.services.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier Management", description = "APIs for managing medicine suppliers")
public class SupplierRestController {

    private final SupplierService supplierService;

    @Operation(
            summary = "Create supplier",
            description = "Creates a new medicine supplier"
    )
    @ApiResponse(responseCode = "201", description = "Supplier created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(
            @RequestBody SupplierDto requestDto) {
        return new ResponseEntity<>(
                supplierService.createSupplier(requestDto),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Get supplier by ID",
            description = "Fetch supplier using ID"
    )
    @ApiResponse(responseCode = "200", description = "Supplier fetched successfully")
    @ApiResponse(responseCode = "404", description = "Supplier not found")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplier(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );
    }

    @Operation(
            summary = "Get all suppliers",
            description = "Fetch all suppliers"
    )
    @ApiResponse(responseCode = "200", description = "Suppliers fetched successfully")
    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );
    }

    @Operation(
            summary = "Update supplier",
            description = "Update supplier details"
    )
    @ApiResponse(responseCode = "200", description = "Supplier updated successfully")
    @ApiResponse(responseCode = "404", description = "Supplier not found")
    @PatchMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierDto requestDto) {
        return ResponseEntity.ok(
                supplierService.updateSupplier(id, requestDto)
        );
    }

    @Operation(
            summary = "Delete supplier",
            description = "Delete supplier by ID"
    )
    @ApiResponse(responseCode = "204", description = "Supplier deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}


