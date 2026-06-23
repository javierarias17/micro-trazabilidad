package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.exception.OrderLogNotFoundException;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.factory.OrderLogModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.pragma.powerup.factory.OrderLogModelFactory.CUSTOMER_ID;
import static com.pragma.powerup.factory.OrderLogModelFactory.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderHistoryUseCaseTest {

    private static final Long OTHER_CUSTOMER_ID = 99L;

    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    @Mock
    private IAuthenticatedUserPort authenticatedUserPort;

    private GetOrderHistoryUseCase getOrderHistoryUseCase;

    @BeforeEach
    void setUp() {
        getOrderHistoryUseCase = new GetOrderHistoryUseCase(orderLogPersistencePort, authenticatedUserPort);
    }

    // ─── Happy path

    @Test
    void When_CustomerRequestsOwnOrderHistory_Expect_LogListReturned() {
        // Arrange
        List<OrderLogModel> logs = OrderLogModelFactory.createLogHistory();
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(CUSTOMER_ID);
        when(orderLogPersistencePort.findAllByOrderId(ORDER_ID)).thenReturn(logs);

        // Act
        List<OrderLogModel> result = getOrderHistoryUseCase.getOrderHistory(ORDER_ID);

        // Assert
        assertEquals(2, result.size());
        assertEquals(ORDER_ID, result.get(0).getOrderId());
        assertEquals(CUSTOMER_ID, result.get(0).getCustomerId());
    }

    // ─── Exception paths

    @Test
    void Expect_OrderLogNotFoundException_When_NoLogsExistForOrder() {
        // Arrange
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(CUSTOMER_ID);
        when(orderLogPersistencePort.findAllByOrderId(ORDER_ID)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(OrderLogNotFoundException.class,
                () -> getOrderHistoryUseCase.getOrderHistory(ORDER_ID));
    }

    @Test
    void Expect_ForbiddenException_When_CustomerRequestsAnotherCustomersOrderHistory() {
        // Arrange
        List<OrderLogModel> logs = OrderLogModelFactory.createLogHistory();
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(OTHER_CUSTOMER_ID);
        when(orderLogPersistencePort.findAllByOrderId(ORDER_ID)).thenReturn(logs);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> getOrderHistoryUseCase.getOrderHistory(ORDER_ID));
    }
}
