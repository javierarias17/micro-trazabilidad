package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import com.pragma.powerup.application.mapper.IOrderLogRequestMapper;
import com.pragma.powerup.application.mapper.IOrderLogResponseMapper;
import com.pragma.powerup.domain.api.ICreateOrderLogServicePort;
import com.pragma.powerup.domain.api.IGetOrderHistoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLogHandler implements IOrderLogHandler {

    private final ICreateOrderLogServicePort createOrderLogServicePort;
    private final IGetOrderHistoryServicePort getOrderHistoryServicePort;
    private final IOrderLogRequestMapper orderLogRequestMapper;
    private final IOrderLogResponseMapper orderLogResponseMapper;

    @Override
    public void createOrderLog(OrderLogRequestDto orderLogRequestDto) {
        createOrderLogServicePort.createOrderLog(orderLogRequestMapper.toModel(orderLogRequestDto));
    }

    @Override
    public List<OrderLogResponseDto> getOrderHistory(Long orderId) {
        return orderLogResponseMapper.toResponseDtoList(getOrderHistoryServicePort.getOrderHistory(orderId));
    }
}
