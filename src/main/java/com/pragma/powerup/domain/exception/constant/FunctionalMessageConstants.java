package com.pragma.powerup.domain.exception.constant;

public class FunctionalMessageConstants {

    private FunctionalMessageConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BUSINESS_VALIDATION_FAILED = "Business validation failed";
    public static final String ORDER_LOG_NOT_FOUND = "No traceability records found for the given order";
    public static final String FIELD_ORDER_ID = "orderId";
}
