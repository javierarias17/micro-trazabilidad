package com.pragma.powerup.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Payload sent by micro-plazoleta each time an order transitions to a new state")
public class OrderLogRequestDto {

        @NotNull(message = "Order ID is required")
        @Schema(description = "ID of the order whose status changed", example = "42")
        private Long orderId;

        @NotNull(message = "Restaurant ID is required")
        @Schema(description = "ID of the restaurant the order belongs to", example = "7")
        private Long restaurantId;

        @NotNull(message = "Customer ID is required")
        @Schema(description = "ID of the customer who placed the order", example = "15")
        private Long customerId;

        @Schema(description = "ID of the employee who handled the transition (null for customer-triggered transitions)", example = "3")
        private Long employeeId;

        @Schema(description = "Status before the transition (null for the first log entry)", example = "PENDIENTE", allowableValues = {
                        "PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO" })
        private String previousStatus;

        @NotBlank(message = "New status is required")
        @Schema(description = "New status after the transition", example = "EN_PREPARACION", allowableValues = {
                        "PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO" })
        private String newStatus;

        @NotNull(message = "Date is required")
        @Schema(description = "Timestamp when the transition occurred", example = "2024-06-22T10:30:00")
        private LocalDateTime date;
}
