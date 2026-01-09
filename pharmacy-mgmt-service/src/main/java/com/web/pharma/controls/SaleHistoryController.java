package com.web.pharma.controls;

import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.services.SaleHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sales/history")
@RequiredArgsConstructor
public class SaleHistoryController {

    private final SaleHistoryService saleHistoryService;


    @Operation(
            summary = "Get all sales",
            description = "Retrieves all sales with customer and user info."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SaleHistoryDto.class))
                    )
            )
    })
    @GetMapping("/all")
    public List<SaleHistoryDto> getAllSales() {
        return saleHistoryService.getAllSales();
    }

    @Operation(
            summary = "Get today's sales",
            description = "Retrieves all sales made today."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Today's sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            )
    })
    @GetMapping("/today")
    public List<SaleHistoryDto> today() {
        return saleHistoryService.getTodaySales();
    }

    @Operation(
            summary = "Get weekly sales",
            description = "Retrieves all sales made during the current week."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weekly sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            )
    })
    @GetMapping("/weekly")
    public List<SaleHistoryDto> weekly() {
        return saleHistoryService.getWeeklySales();
    }

    @Operation(
            summary = "Get monthly sales",
            description = "Retrieves all sales made during the current month."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Monthly sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            )
    })
    @GetMapping("/monthly")
    public List<SaleHistoryDto> monthly() {
        return saleHistoryService.getMonthlySales();
    }

    @Operation(
            summary = "Get sales by date range",
            description = "Retrieves sales within a specific date range."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid date range")
    })
    @GetMapping("/range")
    public List<SaleHistoryDto> dateRange(
            @Parameter(
                    description = "Start date (YYYY-MM-DD)",
                    example = "2025-01-01",
                    required = true
            )
            @RequestParam LocalDate startDate,

            @Parameter(
                    description = "End date (YYYY-MM-DD)",
                    example = "2025-01-31",
                    required = true
            )
            @RequestParam LocalDate endDate
    ) {
        return saleHistoryService.getSalesByDateRange(startDate, endDate);
    }

    @Operation(
            summary = "Get sales by customer",
            description = "Retrieves all sales associated with a specific customer."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer sales retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/customer/{customerId}")
    public List<SaleHistoryDto> customerSales(
            @Parameter(
                    description = "Customer ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long customerId
    ) {
        return saleHistoryService.getSalesByCustomer(customerId);
    }
}

