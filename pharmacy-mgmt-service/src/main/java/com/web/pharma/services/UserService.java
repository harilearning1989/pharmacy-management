package com.web.pharma.services;

import com.web.pharma.dtos.AdminRequestDto;
import com.web.pharma.dtos.PatchUserRequest;
import com.web.pharma.dtos.UpdatePasswordRequest;
import com.web.pharma.dtos.UserResponseDto;
import com.web.pharma.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponseDto> findAll();

    UserResponseDto saveUser(AdminRequestDto request);
    UserResponseDto saveUser(User user, UpdatePasswordRequest request);

    String login(AdminRequestDto request);

    UserResponseDto patchUser(Long id, PatchUserRequest request);

    UserResponseDto updateUsername(Long id, Map<String, String> request);
}
