package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Efficiency data for a single completed order")
public class OrderEfficiencyResponseDto {

    @Schema(description = "ID of the order", example = "42")
    private Long orderId;

    @Schema(description = "Timestamp when the order entered PENDIENTE status", example = "2024-06-22T10:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Timestamp when the order entered ENTREGADO status", example = "2024-06-22T10:30:00")
    private LocalDateTime endDate;

    @Schema(description = "Total duration of the order in seconds", example = "1800")
    private Long durationSeconds;
}
