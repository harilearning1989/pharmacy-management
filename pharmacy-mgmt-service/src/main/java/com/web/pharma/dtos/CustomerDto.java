package com.web.pharma.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer data transfer object")
public record CustomerDto(
        @Schema(description = "Customer ID", example = "1")
        Long id,
        @Schema(description = "Customer name", example = "John Doe")
        String name,
        @Schema(description = "Customer email", example = "john@example.com")
        String email,
        @Schema(description = "Customer phone number", example = "+1234567890")
        String phone
) {}

