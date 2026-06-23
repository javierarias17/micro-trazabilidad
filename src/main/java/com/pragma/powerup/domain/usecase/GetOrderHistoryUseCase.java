package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IGetOrderHistoryServicePort;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.exception.OrderLogNotFoundException;
import com.pragma.powerup.domain.exception.constant.FunctionalMessageConstants;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;

import java.util.List;
import java.util.Map;

public class GetOrderHistoryUseCase implements IGetOrderHistoryServicePort {

    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IAuthenticatedUserPort authenticatedUserPort;

    public GetOrderHistoryUseCase(IOrderLogPersistencePort orderLogPersistencePort,
                                  IAuthenticatedUserPort authenticatedUserPort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public List<OrderLogModel> getOrderHistory(Long orderId) {
        Long requestingCustomerId = authenticatedUserPort.getAuthenticatedUserId();

        List<OrderLogModel> logs = orderLogPersistencePort.findAllByOrderId(orderId);

        if (logs.isEmpty()) {
            throw new OrderLogNotFoundException(
                    FunctionalMessageConstants.BUSINESS_VALIDATION_FAILED,
                    Map.of(FunctionalMessageConstants.FIELD_ORDER_ID,
                            FunctionalMessageConstants.ORDER_LOG_NOT_FOUND));
        }

        if (!logs.get(0).getCustomerId().equals(requestingCustomerId)) {
            throw new ForbiddenException();
        }

        return logs;
    }
}
