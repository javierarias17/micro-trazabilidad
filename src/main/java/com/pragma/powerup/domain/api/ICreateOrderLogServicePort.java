package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderLogModel;

public interface ICreateOrderLogServicePort {
    void createOrderLog(OrderLogModel orderLogModel);
}
