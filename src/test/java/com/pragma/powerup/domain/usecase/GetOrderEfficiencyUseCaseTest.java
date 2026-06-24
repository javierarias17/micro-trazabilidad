package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.EmployeeRankingModel;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.RestaurantEfficiencyModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IPlazoletaServicePort;
import com.pragma.powerup.factory.OrderLogModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.pragma.powerup.factory.OrderLogModelFactory.EMPLOYEE_ID;
import static com.pragma.powerup.factory.OrderLogModelFactory.RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderEfficiencyUseCaseTest {

    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    @Mock
    private IPlazoletaServicePort plazoletaServicePort;

    private GetOrderEfficiencyUseCase getOrderEfficiencyUseCase;

    @BeforeEach
    void setUp() {
        getOrderEfficiencyUseCase = new GetOrderEfficiencyUseCase(
                orderLogPersistencePort, plazoletaServicePort);
    }

    // ─── Happy paths

    @Test
    void When_OwnerRequestsEfficiency_Expect_CompletedOrdersAndRankingReturned() {
        // Arrange
        List<OrderLogModel> logs = OrderLogModelFactory.createCompletedOrderLogs();
        when(plazoletaServicePort.isOwnerOfRestaurant(RESTAURANT_ID)).thenReturn(true);
        when(orderLogPersistencePort.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(logs);

        // Act
        RestaurantEfficiencyModel result = getOrderEfficiencyUseCase.getOrderEfficiency(RESTAURANT_ID);

        // Assert
        assertEquals(1, result.getOrdersEfficiency().size());
        assertEquals(1, result.getEmployeesRanking().size());

        OrderEfficiencyModel orderEfficiency = result.getOrdersEfficiency().get(0);
        assertEquals(OrderLogModelFactory.ORDER_ID, orderEfficiency.getOrderId());
        assertTrue(orderEfficiency.getDurationSeconds() > 0);

        EmployeeRankingModel ranking = result.getEmployeesRanking().get(0);
        assertEquals(EMPLOYEE_ID, ranking.getEmployeeId());
        assertEquals(1, ranking.getTotalOrders());
    }

    @Test
    void When_NoLogsExist_Expect_EmptyLists() {
        // Arrange
        when(plazoletaServicePort.isOwnerOfRestaurant(RESTAURANT_ID)).thenReturn(true);
        when(orderLogPersistencePort.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(Collections.emptyList());

        // Act
        RestaurantEfficiencyModel result = getOrderEfficiencyUseCase.getOrderEfficiency(RESTAURANT_ID);

        // Assert
        assertTrue(result.getOrdersEfficiency().isEmpty());
        assertTrue(result.getEmployeesRanking().isEmpty());
    }

    @Test
    void When_OrderNotCompleted_Expect_ExcludedFromEfficiency() {
        // Arrange: only PENDIENTE and EN_PREPARACION logs — no ENTREGADO
        List<OrderLogModel> logs = OrderLogModelFactory.createLogHistory();
        when(plazoletaServicePort.isOwnerOfRestaurant(RESTAURANT_ID)).thenReturn(true);
        when(orderLogPersistencePort.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(logs);

        // Act
        RestaurantEfficiencyModel result = getOrderEfficiencyUseCase.getOrderEfficiency(RESTAURANT_ID);

        // Assert
        assertTrue(result.getOrdersEfficiency().isEmpty());
        assertTrue(result.getEmployeesRanking().isEmpty());
    }

    @Test
    void When_MultipleEmployees_Expect_RankingSortedByAverageDurationAscending() {
        // Arrange: two orders handled by different employees
        List<OrderLogModel> logs = OrderLogModelFactory.createMultiEmployeeLogs();
        when(plazoletaServicePort.isOwnerOfRestaurant(RESTAURANT_ID)).thenReturn(true);
        when(orderLogPersistencePort.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(logs);

        // Act
        RestaurantEfficiencyModel result = getOrderEfficiencyUseCase.getOrderEfficiency(RESTAURANT_ID);

        // Assert
        List<EmployeeRankingModel> ranking = result.getEmployeesRanking();
        assertEquals(2, ranking.size());
        assertTrue(ranking.get(0).getAverageDurationSeconds()
                <= ranking.get(1).getAverageDurationSeconds());
    }

    // ─── Exception paths

    @Test
    void Expect_ForbiddenException_When_OwnerDoesNotOwnRestaurant() {
        // Arrange
        when(plazoletaServicePort.isOwnerOfRestaurant(RESTAURANT_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> getOrderEfficiencyUseCase.getOrderEfficiency(RESTAURANT_ID));
    }
}
