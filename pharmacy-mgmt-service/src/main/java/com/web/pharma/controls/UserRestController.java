package com.web.pharma.controls;

import com.web.pharma.dtos.AdminRequestDto;
import com.web.pharma.dtos.PatchUserRequest;
import com.web.pharma.dtos.UpdatePasswordRequest;
import com.web.pharma.dtos.UserResponseDto;
import com.web.pharma.models.User;
import com.web.pharma.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody AdminRequestDto request) {
        return userService.saveUser(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }

    @PatchMapping("/{id}")
    public UserResponseDto patchUser(@PathVariable Long id,
                                     @RequestBody PatchUserRequest request) {
        return userService.patchUser(id,request);
    }

    @PatchMapping("/change/password")
    public UserResponseDto changePassword(@AuthenticationPrincipal User user,
                                          @RequestBody UpdatePasswordRequest request) {
        return userService.saveUser(user, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/username")
    public UserResponseDto updateUsername(@PathVariable Long id,
                                          @RequestBody Map<String, String> request) {
        return userService.updateUsername(id,request);
    }

    @PostMapping("/login")
    public String login(@RequestBody AdminRequestDto request) {
        return userService.login(request);
    }

}
