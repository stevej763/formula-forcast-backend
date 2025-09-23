package com.steve.formulaforecast.persistence.entity.account;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record AccountAuthenticationUserDetailsEntity(
        UUID accountUid,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Instant createdAt,
        String password
) implements UserDetails
{
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password();
    }

    @Override
    public String getUsername() {
        return email();
    }
}
