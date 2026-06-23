package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.domain.model.OrderLogModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderLogResponseMapper {
    OrderLogResponseDto toResponseDto(OrderLogModel orderLogModel);
    List<OrderLogResponseDto> toResponseDtoList(List<OrderLogModel> orderLogModels);
}
