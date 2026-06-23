package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAuthenticatedUserAdapter implements IAuthenticatedUserPort {

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Long getAuthenticatedUserId() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getDetails();
    }

    @Override
    public String getAuthenticatedUserRole() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace(ROLE_PREFIX, ""))
                .orElse(null);
    }
}
