package com.web.pharma.dtos;

public record UpdatePasswordRequest(
        String currentPassword,
        String newPassword
) {}

