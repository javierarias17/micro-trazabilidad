package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;

import java.util.List;

public interface IOrderLogHandler {
    void createOrderLog(OrderLogRequestDto orderLogRequestDto);
    List<OrderLogResponseDto> getOrderHistory(Long orderId);
}
