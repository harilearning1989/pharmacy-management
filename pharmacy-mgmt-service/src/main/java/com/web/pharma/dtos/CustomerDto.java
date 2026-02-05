package com.web.pharma.dtos;

import com.web.pharma.models.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Customer data transfer object")
public record CustomerDto(

        @Schema(description = "Customer ID", example = "1")
        Long id,

        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 50)
        @Schema(description = "Customer name", example = "John Doe")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        @Schema(description = "Customer email", example = "john@example.com")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "Invalid phone number"
        )
        @Schema(description = "Customer phone number", example = "+1234567890")
        String phone,

        @NotBlank(message = "Gender is mandatory")
        @Pattern(
                regexp = "Male|Female|Other",
                message = "Gender must be Male, Female, or Other"
        )
        @Schema(description = "Customer gender", example = "Male")
        String gender,

        /*@NotNull(message = "Date of birth is mandatory")
        @Past(message = "Date of birth must be in the past")
        @Schema(description = "Customer dob", example = "1989-04-06")
        LocalDate dob*/
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

