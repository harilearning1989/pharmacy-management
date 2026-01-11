package com.web.pharma.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    // 🔑 Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👤 Login
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // 📧 Contact Info
    @Column(unique = true, length = 150)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    // 🔐 Account status flags
    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Column(name = "account_non_expired", nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired = true;

    // ⚡ Login attempts
    @Column(name = "failed_login_attempts", nullable = false)
    @Builder.Default
    private int failedLoginAttempts = 0;

    @Column(name = "last_failed_login")
    private LocalDateTime lastFailedLogin;

    // 🕒 Audit
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ⏳ Credentials expiry
    @Column(name = "credentials_expiry_date")
    private LocalDateTime credentialsExpiryDate;

    // 🔗 Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // =============================
    // Auto-set timestamps and default expiry
    // =============================
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.credentialsExpiryDate = this.createdAt.plusMonths(3); // 3 months default
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // =============================
    // Failed login handling
    // =============================
    public void incrementFailedAttempts() {
        this.failedLoginAttempts++;
        this.lastFailedLogin = LocalDateTime.now();
        if (this.failedLoginAttempts >= 5) { // Lock account after 5 failed attempts
            this.accountNonLocked = false;
        }
    }

    public void resetFailedAttempts() {
        this.failedLoginAttempts = 0;
        this.lastFailedLogin = null;
        this.accountNonLocked = true;
    }

    // =============================
    // Spring Security Overrides
    // =============================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpiryDate == null || LocalDateTime.now().isBefore(credentialsExpiryDate);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}







