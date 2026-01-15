package com.web.pharma.controls;

import com.web.pharma.dtos.SaleHistoryDetailDto;
import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.dtos.SalesSummaryResponseDto;
import com.web.pharma.services.SaleHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    // 🔹 FIND BY SALE ID
    @Operation(
            summary = "Get sale by ID",
            description = "Retrieves a single sale by its sale ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sale retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleHistoryDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sale not found"
            )
    })
    @GetMapping("/saleById")
    public SaleHistoryDetailDto getSaleById(@RequestParam("saleId") Long saleId) {
        return saleHistoryService.getSaleById(saleId);
    }

    @Operation(
            summary = "Get today's sales summary",
            description = "Retrieves today's sales with total count, amount, discount, and GST collected."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Today's sales summary retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalesSummaryResponseDto.class)
                    )
            )
    })
    @GetMapping("/today")
    public SalesSummaryResponseDto todaySalesSummary() {
        return saleHistoryService.getTodaySalesSummary();
    }

    @Operation(
            summary = "Get this week's sales summary",
            description = "Retrieves this week's sales with totals and sales list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "This week's sales summary retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalesSummaryResponseDto.class)
                    )
            )
    })
    @GetMapping("/week")
    public SalesSummaryResponseDto weekSalesSummary() {
        return saleHistoryService.getThisWeekSalesSummary();
    }

    @Operation(
            summary = "Get this month's sales summary",
            description = "Retrieves this month's sales with totals and sales list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "This month's sales summary retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalesSummaryResponseDto.class)
                    )
            )
    })
    @GetMapping("/month")
    public SalesSummaryResponseDto monthSalesSummary() {
        return saleHistoryService.getThisMonthSalesSummary();
    }

    @Operation(
            summary = "Get sales summary by date range",
            description = "Retrieves sales summary for a specific date range."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales summary retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalesSummaryResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid date range")
    })
    @GetMapping("/summary")
    public SalesSummaryResponseDto salesSummaryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return saleHistoryService.getSalesSummaryByDateRange(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
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

    @Operation(
            summary = "Get total sales count",
            description = "Retrieves the total number of sales in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Total sales count retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )
            )
    })
    @GetMapping("/count")
    public Long totalSalesCount() {
        return saleHistoryService.getTotalSalesCount();
    }

}

