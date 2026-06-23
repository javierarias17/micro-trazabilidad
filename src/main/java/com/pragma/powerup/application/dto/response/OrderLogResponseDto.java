package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Single traceability entry representing one status transition of an order")
public class OrderLogResponseDto {

    @Schema(description = "Unique MongoDB document ID of this log entry", example = "6672a1b3c4d5e6f7a8b9c0d1")
    private String id;

    @Schema(description = "ID of the order", example = "42")
    private Long orderId;

    @Schema(description = "ID of the restaurant", example = "7")
    private Long restaurantId;

    @Schema(description = "ID of the customer who placed the order", example = "15")
    private Long customerId;

    @Schema(description = "ID of the employee who performed the transition (null for customer-triggered transitions)", example = "3")
    private Long employeeId;

    @Schema(description = "Status before the transition (null for the first entry)", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO"})
    private String previousStatus;

    @Schema(description = "Status after the transition", example = "EN_PREPARACION",
            allowableValues = {"PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO"})
    private String newStatus;

    @Schema(description = "Timestamp when the transition occurred", example = "2024-06-22T10:30:00")
    private LocalDateTime date;
}
