package com.web.pharma.controls;

import com.web.pharma.dtos.SaleRequestDto;
import com.web.pharma.dtos.SaleResponseDto;
import com.web.pharma.models.Sale;
import com.web.pharma.models.User;
import com.web.pharma.services.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleRestController {

    private final SaleService saleService;

    /*{
      "customerId": 1,
          "paymentMethod": "CASH",
          "items": [
      { "medicineId": 10, "quantity": 2 },
      { "medicineId": 12, "quantity": 1 }
]
  }*/

    /*@Operation(
            summary = "Create a new sale",
            description = "Creates a new sale for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sale successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid sale data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public SaleResponseDto createSale(
            @RequestBody SaleRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return saleService.createSale(dto, userDetails.getUsername());
    }*/

    @Operation(
            summary = "Create a new sale",
            description = "Creates a new sale for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sale successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid sale data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public SaleResponseDto createSaleMedicine(
            @RequestBody SaleRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return saleService.createSaleMedicine(dto, userDetails.getUsername());
    }

    @Operation(
            summary = "Get all sales",
            description = "Retrieves a list of all sales."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<SaleResponseDto> getAllSales() {
        return saleService.getAllSales();
    }

    @Operation(
            summary = "Get sale by ID",
            description = "Retrieves a sale by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sale retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Sale.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public Sale getSale(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @Operation(
            summary = "Delete a sale",
            description = "Deletes a sale by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sale deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }
}

