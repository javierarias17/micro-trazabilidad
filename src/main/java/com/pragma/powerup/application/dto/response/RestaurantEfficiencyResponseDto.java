package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Efficiency report for a restaurant: per-order durations and employee ranking")
public class RestaurantEfficiencyResponseDto {

    @Schema(description = "List of completed orders with their start/end times and total duration")
    private List<OrderEfficiencyResponseDto> ordersEfficiency;

    @Schema(description = "Ranking of employees by average order duration, sorted from fastest to slowest")
    private List<EmployeeRankingResponseDto> employeesRanking;
}
