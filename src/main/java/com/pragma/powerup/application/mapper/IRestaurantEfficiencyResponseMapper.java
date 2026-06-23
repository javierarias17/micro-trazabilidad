package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.powerup.domain.model.RestaurantEfficiencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEfficiencyResponseMapper {
    RestaurantEfficiencyResponseDto toResponseDto(RestaurantEfficiencyModel model);
}
