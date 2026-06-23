package com.pragma.powerup.domain.exception;

import java.util.Map;

public class OrderLogNotFoundException extends FunctionalException {
    public OrderLogNotFoundException(String message, Map<String, String> errors) {
        super(message, errors);
    }
}
