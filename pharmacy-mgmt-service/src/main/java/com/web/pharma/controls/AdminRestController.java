package com.web.pharma.controls;

import com.web.pharma.dtos.AdminRequestDto;
import com.web.pharma.models.Role;
import com.web.pharma.models.User;
import com.web.pharma.repos.RoleRepository;
import com.web.pharma.repos.UserRepository;
import com.web.pharma.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtService;
    private final RoleRepository roleRepository;

    @PostMapping("/register")
    public void registerUser(@RequestBody AdminRequestDto request) {
        Role userRole = roleRepository.findByName(request.role())
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found in database"));

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AdminRequestDto request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if account is enabled
        if (!user.isEnabled()) {
            throw new RuntimeException("Account is disabled");
        }

        // Check if account is expired
        if (!user.isAccountNonExpired()) {
            throw new RuntimeException("Account has expired");
        }

        // Check if account is locked
        if (!user.isAccountNonLocked()) {
            throw new RuntimeException("Account is locked due to too many failed login attempts");
        }

        // Check if credentials are expired
        if (!user.isCredentialsNonExpired() ||
                (user.getCredentialsExpiryDate() != null && user.getCredentialsExpiryDate().isBefore(LocalDateTime.now()))) {
            throw new RuntimeException("Credentials have expired. Please reset your password.");
        }

        // Validate password
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            // Increment failed attempts
            user.incrementFailedAttempts();
            userRepository.save(user);
            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts after successful login
        user.resetFailedAttempts();
        userRepository.save(user);

        // Generate JWT token
        return jwtService.generateToken(user.getUsername());
    }

}
