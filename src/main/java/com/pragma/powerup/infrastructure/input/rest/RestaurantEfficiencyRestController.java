package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.powerup.application.handler.IRestaurantEfficiencyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantEfficiencyRestController implements IRestaurantEfficiencyRestControllerDocs {

    private final IRestaurantEfficiencyHandler restaurantEfficiencyHandler;

    @Override
    @GetMapping("/{restaurantId}/efficiency")
    public ResponseEntity<RestaurantEfficiencyResponseDto> getOrderEfficiency(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantEfficiencyHandler.getOrderEfficiency(restaurantId));
    }
}
