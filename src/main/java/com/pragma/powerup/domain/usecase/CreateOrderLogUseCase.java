package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICreateOrderLogServicePort;
import com.pragma.powerup.domain.common.FieldConstants;
import com.pragma.powerup.domain.common.ValidationMessageConstants;
import com.pragma.powerup.domain.exception.FieldsValidationException;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.validator.FieldValidator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CreateOrderLogUseCase implements ICreateOrderLogServicePort {

    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final String ROLE_EMPLOYEE = "EMPLOYEE";

    private static final Set<OrderStatus> CUSTOMER_ALLOWED_STATUSES =
            Set.of(OrderStatus.PENDIENTE, OrderStatus.CANCELADO);
    private static final Set<OrderStatus> EMPLOYEE_ALLOWED_STATUSES =
            Set.of(OrderStatus.EN_PREPARACION, OrderStatus.LISTO, OrderStatus.ENTREGADO);

    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IAuthenticatedUserPort authenticatedUserPort;

    public CreateOrderLogUseCase(IOrderLogPersistencePort orderLogPersistencePort,
                                 IAuthenticatedUserPort authenticatedUserPort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public void createOrderLog(OrderLogModel orderLogModel) {
        this.validateFields(orderLogModel);
        this.validateRolePermission(orderLogModel.getNewStatus());
        orderLogPersistencePort.save(orderLogModel);
    }

    private void validateFields(OrderLogModel orderLogModel) {
        Map<String, String> errors = new LinkedHashMap<>();

        FieldValidator.validateNotNull(orderLogModel.getOrderId(),
                FieldConstants.ORDER_ID, ValidationMessageConstants.MSG_ORDER_ID_REQUIRED, errors);
        FieldValidator.validateNotNull(orderLogModel.getRestaurantId(),
                FieldConstants.RESTAURANT_ID, ValidationMessageConstants.MSG_RESTAURANT_ID_REQUIRED, errors);
        FieldValidator.validateNotNull(orderLogModel.getCustomerId(),
                FieldConstants.CUSTOMER_ID, ValidationMessageConstants.MSG_CUSTOMER_ID_REQUIRED, errors);
        FieldValidator.validateNotBlank(orderLogModel.getNewStatus(),
                FieldConstants.NEW_STATUS, ValidationMessageConstants.MSG_NEW_STATUS_REQUIRED, errors);
        FieldValidator.validateEnum(orderLogModel.getNewStatus(), OrderStatus.class,
                FieldConstants.NEW_STATUS, ValidationMessageConstants.MSG_INVALID_STATUS, errors);
        FieldValidator.validateEnum(orderLogModel.getPreviousStatus(), OrderStatus.class,
                FieldConstants.PREVIOUS_STATUS, ValidationMessageConstants.MSG_INVALID_STATUS, errors);
        FieldValidator.validateNotNull(orderLogModel.getDate(),
                FieldConstants.DATE, ValidationMessageConstants.MSG_DATE_REQUIRED, errors);

        if (!errors.isEmpty())
            throw new FieldsValidationException(errors);
    }

    private void validateRolePermission(String newStatus) {
        OrderStatus status = OrderStatus.valueOf(newStatus);
        String role = authenticatedUserPort.getAuthenticatedUserRole();

        if (ROLE_CUSTOMER.equals(role) && !CUSTOMER_ALLOWED_STATUSES.contains(status))
            throw new ForbiddenException();
        if (ROLE_EMPLOYEE.equals(role) && !EMPLOYEE_ALLOWED_STATUSES.contains(status))
            throw new ForbiddenException();
    }
}
