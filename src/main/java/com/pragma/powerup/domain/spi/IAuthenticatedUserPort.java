package com.pragma.powerup.domain.spi;

public interface IAuthenticatedUserPort {
    Long getAuthenticatedUserId();
    String getAuthenticatedUserRole();
}
