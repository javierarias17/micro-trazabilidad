package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEfficiencyModel {
    private List<OrderEfficiencyModel> ordersEfficiency;
    private List<EmployeeRankingModel> employeesRanking;
}
