package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Efficiency ranking entry for a single employee")
public class EmployeeRankingResponseDto {

    @Schema(description = "ID of the employee", example = "3")
    private Long employeeId;

    @Schema(description = "Average order duration handled by this employee in seconds", example = "1500.0")
    private Double averageDurationSeconds;

    @Schema(description = "Total number of completed orders handled by this employee", example = "4")
    private Integer totalOrders;
}
