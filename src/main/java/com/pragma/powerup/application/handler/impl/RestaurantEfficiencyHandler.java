package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.powerup.application.handler.IRestaurantEfficiencyHandler;
import com.pragma.powerup.application.mapper.IRestaurantEfficiencyResponseMapper;
import com.pragma.powerup.domain.api.IGetOrderEfficiencyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantEfficiencyHandler implements IRestaurantEfficiencyHandler {

    private final IGetOrderEfficiencyServicePort getOrderEfficiencyServicePort;
    private final IRestaurantEfficiencyResponseMapper restaurantEfficiencyResponseMapper;

    @Override
    public RestaurantEfficiencyResponseDto getOrderEfficiency(Long restaurantId) {
        return restaurantEfficiencyResponseMapper.toResponseDto(
                getOrderEfficiencyServicePort.getOrderEfficiency(restaurantId));
    }
}
