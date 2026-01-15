package com.web.pharma.controls;

import com.web.pharma.dtos.CustomerDto;
import com.web.pharma.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")  // JWT Bearer token
public class CustomerRestController {

    private final CustomerService service;

    @PostMapping
    @Operation(summary = "Register a new customer", description = "Creates a new customer. Admin role required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto dto) {
        return ResponseEntity.ok(service.registerCustomer(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing customer", description = "Update customer details by ID. Admin role required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions")
    })
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody CustomerDto dto) {
        return ResponseEntity.ok(service.updateCustomer(id, dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by ID", description = "Retrieve customer details using their ID. Admin role required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions")
    })
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers. Admin role required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions")
    })
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @GetMapping("/searchName")
    @Operation(summary = "Search customers", description = "Search customers by name (min 3 characters, case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Search keyword must be at least 3 characters"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions")
    })
    public ResponseEntity<List<CustomerDto>> searchCustomerByName(
            @RequestParam String name) {
        return ResponseEntity.ok(service.searchCustomersByName(name));
    }

    @GetMapping("/searchPhone")
    @Operation(summary = "Search customers", description = "Search customers by phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Search keyword must be at least 3 characters"),
            @ApiResponse(responseCode = "403", description = "Forbidden, insufficient permissions")
    })
    public ResponseEntity<List<CustomerDto>> searchCustomerByPhone(
            @RequestParam String phone) {
        return ResponseEntity.ok(service.searchCustomersByPhone(phone));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get total customer count",
            description = "Retrieve the total number of customers. Admin role required."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer count retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden, insufficient permissions"
            )
    })
    public ResponseEntity<Long> getCustomerCount() {
        return ResponseEntity.ok(service.getCustomerCount());
    }


}

