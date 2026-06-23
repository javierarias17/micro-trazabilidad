package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.RestaurantEfficiencyResponseDto;

public interface IRestaurantEfficiencyHandler {
    RestaurantEfficiencyResponseDto getOrderEfficiency(Long restaurantId);
}
