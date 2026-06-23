package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.FieldsValidationException;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.factory.OrderLogModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderLogUseCaseTest {

    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String BLANK_STATUS = "   ";
    private static final String INVALID_STATUS = "INVALID_STATUS";

    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    @Mock
    private IAuthenticatedUserPort authenticatedUserPort;

    private CreateOrderLogUseCase createOrderLogUseCase;

    @BeforeEach
    void setUp() {
        createOrderLogUseCase = new CreateOrderLogUseCase(orderLogPersistencePort, authenticatedUserPort);
    }

    // ─── Happy paths

    @Test
    void When_CustomerRoleAndPendienteStatus_Expect_LogSaved() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_CUSTOMER);

        // Act & Assert
        assertDoesNotThrow(() -> createOrderLogUseCase.createOrderLog(request));
        verify(orderLogPersistencePort).save(request);
    }

    @Test
    void When_CustomerRoleAndCanceladoStatus_Expect_LogSaved() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.CANCELADO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_CUSTOMER);

        // Act & Assert
        assertDoesNotThrow(() -> createOrderLogUseCase.createOrderLog(request));
        verify(orderLogPersistencePort).save(request);
    }

    @Test
    void When_EmployeeRoleAndEnPreparacionStatus_Expect_LogSaved() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.EN_PREPARACION);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_EMPLOYEE);

        // Act & Assert
        assertDoesNotThrow(() -> createOrderLogUseCase.createOrderLog(request));
        verify(orderLogPersistencePort).save(request);
    }

    @Test
    void When_EmployeeRoleAndListoStatus_Expect_LogSaved() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.LISTO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_EMPLOYEE);

        // Act & Assert
        assertDoesNotThrow(() -> createOrderLogUseCase.createOrderLog(request));
        verify(orderLogPersistencePort).save(request);
    }

    @Test
    void When_EmployeeRoleAndEntregadoStatus_Expect_LogSaved() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.ENTREGADO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_EMPLOYEE);

        // Act & Assert
        assertDoesNotThrow(() -> createOrderLogUseCase.createOrderLog(request));
        verify(orderLogPersistencePort).save(request);
    }

    // ─── Exception paths

    @Test
    void Expect_FieldsValidationException_When_OrderIdIsNull() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setOrderId(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_RestaurantIdIsNull() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setRestaurantId(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_CustomerIdIsNull() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setCustomerId(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_NewStatusIsNull() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setNewStatus(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_NewStatusIsBlank() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setNewStatus(BLANK_STATUS);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_NewStatusIsInvalid() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setNewStatus(INVALID_STATUS);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_PreviousStatusIsInvalid() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setPreviousStatus(INVALID_STATUS);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_FieldsValidationException_When_DateIsNull() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        request.setDate(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_ForbiddenException_When_CustomerTriesToSetEnPreparacionStatus() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.EN_PREPARACION);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_CUSTOMER);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_ForbiddenException_When_CustomerTriesToSetListoStatus() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.LISTO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_CUSTOMER);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_ForbiddenException_When_CustomerTriesToSetEntregadoStatus() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.ENTREGADO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_CUSTOMER);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_ForbiddenException_When_EmployeeTriesToSetPendienteStatus() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequest();
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_EMPLOYEE);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }

    @Test
    void Expect_ForbiddenException_When_EmployeeTriesToSetCanceladoStatus() {
        // Arrange
        OrderLogModel request = OrderLogModelFactory.createValidRequestWithStatus(OrderStatus.CANCELADO);
        when(authenticatedUserPort.getAuthenticatedUserRole()).thenReturn(ROLE_EMPLOYEE);

        // Act & Assert
        assertThrows(ForbiddenException.class,
                () -> createOrderLogUseCase.createOrderLog(request));
    }
}