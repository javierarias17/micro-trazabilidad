package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestaurantEfficiencyModel;

public interface IGetOrderEfficiencyServicePort {
    RestaurantEfficiencyModel getOrderEfficiency(Long restaurantId);
}
