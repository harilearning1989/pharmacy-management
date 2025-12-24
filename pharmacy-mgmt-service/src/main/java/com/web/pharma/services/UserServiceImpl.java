package com.web.pharma.services;

import com.web.pharma.dtos.AdminRequestDto;
import com.web.pharma.dtos.PatchUserRequest;
import com.web.pharma.dtos.UpdatePasswordRequest;
import com.web.pharma.dtos.UserResponseDto;
import com.web.pharma.models.Role;
import com.web.pharma.models.User;
import com.web.pharma.repos.RoleRepository;
import com.web.pharma.repos.UserRepository;
import com.web.pharma.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtService;

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::fromEntity)
                .toList();
    }

    @Override
    public UserResponseDto saveUser(AdminRequestDto request) {
        /*Role userRole = roleRepository.findByName(request.role())
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found in database"));*/
        Set<Role> roles = getUserRoles(request.roles());

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();

        user = userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }

    private Set<Role> getUserRoles(Set<String> roles) {
        return roles.stream()
                .map(roleName ->
                        roleRepository.findByName(roleName)
                                .orElseThrow(() ->
                                        new IllegalStateException("Role " + roleName + " not found")
                                )
                )
                .collect(Collectors.toSet());
    }

    @Override
    public UserResponseDto saveUser(User user, UpdatePasswordRequest request) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        // Reset security state
        user.resetFailedAttempts();
        user.setCredentialsExpiryDate(LocalDateTime.now().plusMonths(3));

        user = userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }

    @Override
    public String login(AdminRequestDto request) {
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

    @Override
    public UserResponseDto patchUser(Long id, PatchUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.enabled() != null)
            user.setEnabled(request.enabled());

        if (request.accountNonLocked() != null) {
            user.setAccountNonLocked(request.accountNonLocked());
            if (request.accountNonLocked()) {
                user.resetFailedAttempts();
            }
        }

        if (request.accountNonExpired() != null)
            user.setAccountNonExpired(request.accountNonExpired());

        if (request.credentialsNonExpired() != null)
            user.setCredentialsNonExpired(request.credentialsNonExpired());

        if (request.credentialsExpiryDate() != null)
            user.setCredentialsExpiryDate(request.credentialsExpiryDate());

        user = userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    @Override
    public UserResponseDto updateUsername(Long id, Map<String, String> request) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.setUsername(request.get("username"));
        user = userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }
}
