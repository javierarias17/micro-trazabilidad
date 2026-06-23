package com.pragma.powerup.domain.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class FunctionalException extends RuntimeException {

    private final Map<String, String> errors;

    protected FunctionalException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
