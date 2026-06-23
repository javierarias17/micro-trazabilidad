package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderLogModel;

import java.util.List;

public interface IGetOrderHistoryServicePort {
    List<OrderLogModel> getOrderHistory(Long orderId);
}
