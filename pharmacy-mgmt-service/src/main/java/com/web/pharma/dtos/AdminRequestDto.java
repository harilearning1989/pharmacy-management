package com.web.pharma.dtos;

import java.util.Set;

public record AdminRequestDto(String username, String password, Set<String> roles, String email, String phoneNumber) {
}
