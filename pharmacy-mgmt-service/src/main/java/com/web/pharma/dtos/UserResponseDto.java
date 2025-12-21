package com.web.pharma.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.pharma.models.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String phoneNumber,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        int failedLoginAttempts,

        @JsonFormat(pattern = "dd-MMMM-yyyy hh:mm:ss a", locale = "en")
        LocalDateTime credentialsExpiryDate,

        @JsonFormat(pattern = "dd-MMMM-yyyy hh:mm:ss a", locale = "en")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd-MMMM-yyyy hh:mm:ss a", locale = "en")
        LocalDateTime updatedAt
) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.getFailedLoginAttempts(),
                user.getCredentialsExpiryDate(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}