package com.web.pharma.dtos;

import java.time.LocalDateTime;

public record PatchUserRequest(
        Boolean enabled,
        Boolean accountNonLocked,
        Boolean accountNonExpired,
        Boolean credentialsNonExpired,
        LocalDateTime credentialsExpiryDate
) {}

