package com.web.pharma.dtos;

import java.time.LocalDateTime;

public record UpdateUserRequest(
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        LocalDateTime credentialsExpiryDate
) {}

