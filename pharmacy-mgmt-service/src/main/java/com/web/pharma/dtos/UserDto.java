package com.web.pharma.dtos;

import com.web.pharma.models.User;

public record UserDto(
        Long id,
        String username,
        String email,
        String phone
) {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
