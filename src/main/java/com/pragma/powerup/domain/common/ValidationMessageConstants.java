package com.pragma.powerup.domain.common;

public class ValidationMessageConstants {

    private ValidationMessageConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MSG_ORDER_ID_REQUIRED = "Order ID is required";
    public static final String MSG_RESTAURANT_ID_REQUIRED = "Restaurant ID is required";
    public static final String MSG_CUSTOMER_ID_REQUIRED = "Customer ID is required";
    public static final String MSG_NEW_STATUS_REQUIRED = "New status is required";
    public static final String MSG_DATE_REQUIRED = "Date is required";
    public static final String MSG_INVALID_STATUS = "Status must be one of: PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO";
}
