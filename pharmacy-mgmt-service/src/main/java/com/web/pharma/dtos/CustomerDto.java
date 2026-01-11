package com.web.pharma.dtos;

import com.web.pharma.models.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Customer data transfer object")
public record CustomerDto(
        @Schema(description = "Customer ID", example = "1")
        Long id,
        @Schema(description = "Customer name", example = "John Doe")
        String name,
        @Schema(description = "Customer email", example = "john@example.com")
        String email,
        @Schema(description = "Customer phone number", example = "+1234567890")
        String phone,
        @Schema(description = "Customer gender", example = "Male")
        String gender,
        @Schema(description = "Customer dob", example = "06-04-1989")
        String dob

) {
    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getGender(),
                customer.getDob().toString()
        );
    }

    public static Customer toEntity(CustomerDto customerDto) {
        LocalDate dob = LocalDate.parse(customerDto.dob());
        return Customer.builder()
                .name(customerDto.name)
                .email(customerDto.email)
                .phone(customerDto.phone)
                .gender(customerDto.gender.toUpperCase())
                .dob(dob)
                .build();
    }
}

