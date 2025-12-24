package com.web.pharma.controls;

import com.web.pharma.dtos.*;
import com.web.pharma.models.User;
import com.web.pharma.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully registered",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody AdminRequestDto request) {
        return userService.saveUser(request);
    }

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users. Admin role required."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden, admin access required")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }

    @Operation(
            summary = "Update user partially",
            description = "Updates selected user fields by user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PatchMapping("/{id}")
    public UserResponseDto patchUser(
            @PathVariable Long id,
            @RequestBody PatchUserRequest request) {
        return userService.patchUser(id, request);
    }

    @Operation(
            summary = "Change user password",
            description = "Allows authenticated users to change their password."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid password data")
    })
    @PatchMapping("/change/password")
    public UserResponseDto changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody UpdatePasswordRequest request) {
        return userService.saveUser(user, request);
    }

    @Operation(
            summary = "Update username",
            description = "Updates a user's username by ID. Admin role required."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Username updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden, admin access required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/username")
    public UserResponseDto updateUsername(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        return userService.updateUsername(id, request);
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful, token returned",
                    content = @Content(
                            mediaType = "text/plain"
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody AdminRequestDto request) {
        String token = userService.login(request);
        return new LoginResponseDto(token);
    }
}

