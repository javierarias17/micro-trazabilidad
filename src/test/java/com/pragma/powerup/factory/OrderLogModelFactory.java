package com.pragma.powerup.factory;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderLogModelFactory {

    public static final Long ORDER_ID = 42L;
    public static final Long RESTAURANT_ID = 7L;
    public static final Long CUSTOMER_ID = 15L;
    public static final Long EMPLOYEE_ID = 3L;
    public static final LocalDateTime DATE_FIRST_TRANSITION = LocalDateTime.of(2024, 6, 22, 10, 0);
    public static final LocalDateTime DATE_SECOND_TRANSITION = LocalDateTime.of(2024, 6, 22, 10, 15);

    private OrderLogModelFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static OrderLogModel createValidRequest() {
        return OrderLogModel.builder()
                .orderId(ORDER_ID)
                .restaurantId(RESTAURANT_ID)
                .customerId(CUSTOMER_ID)
                .employeeId(null)
                .previousStatus(null)
                .newStatus(OrderStatus.PENDIENTE.name())
                .date(DATE_FIRST_TRANSITION)
                .build();
    }

    public static OrderLogModel createValidRequestWithStatus(OrderStatus newStatus) {
        return OrderLogModel.builder()
                .orderId(ORDER_ID)
                .restaurantId(RESTAURANT_ID)
                .customerId(CUSTOMER_ID)
                .employeeId(EMPLOYEE_ID)
                .previousStatus(OrderStatus.PENDIENTE.name())
                .newStatus(newStatus.name())
                .date(DATE_SECOND_TRANSITION)
                .build();
    }

    public static OrderLogModel createSavedLog(OrderStatus previousStatus, OrderStatus newStatus) {
        return OrderLogModel.builder()
                .id("6672a1b3c4d5e6f7a8b9c0d1")
                .orderId(ORDER_ID)
                .restaurantId(RESTAURANT_ID)
                .customerId(CUSTOMER_ID)
                .employeeId(EMPLOYEE_ID)
                .previousStatus(previousStatus.name())
                .newStatus(newStatus.name())
                .date(DATE_SECOND_TRANSITION)
                .build();
    }

    public static List<OrderLogModel> createLogHistory() {
        return List.of(
                OrderLogModel.builder()
                        .id("6672a1b3c4d5e6f7a8b9c0d1")
                        .orderId(ORDER_ID)
                        .restaurantId(RESTAURANT_ID)
                        .customerId(CUSTOMER_ID)
                        .previousStatus(null)
                        .newStatus(OrderStatus.PENDIENTE.name())
                        .date(DATE_FIRST_TRANSITION)
                        .build(),
                OrderLogModel.builder()
                        .id("6672a1b3c4d5e6f7a8b9c0d2")
                        .orderId(ORDER_ID)
                        .restaurantId(RESTAURANT_ID)
                        .customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.PENDIENTE.name())
                        .newStatus(OrderStatus.EN_PREPARACION.name())
                        .date(DATE_SECOND_TRANSITION)
                        .build()
        );
    }

    public static List<OrderLogModel> createCompletedOrderLogs() {
        return List.of(
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .previousStatus(null).newStatus(OrderStatus.PENDIENTE.name())
                        .date(DATE_FIRST_TRANSITION).build(),
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.PENDIENTE.name()).newStatus(OrderStatus.EN_PREPARACION.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(5)).build(),
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.EN_PREPARACION.name()).newStatus(OrderStatus.LISTO.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(25)).build(),
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.LISTO.name()).newStatus(OrderStatus.ENTREGADO.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(30)).build()
        );
    }

    public static List<OrderLogModel> createMultiEmployeeLogs() {
        Long secondOrderId = 43L;
        Long secondEmployeeId = 5L;
        return List.of(
                // Order 42 — employee 3 — 10 min
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .previousStatus(null).newStatus(OrderStatus.PENDIENTE.name())
                        .date(DATE_FIRST_TRANSITION).build(),
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.PENDIENTE.name()).newStatus(OrderStatus.EN_PREPARACION.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(1)).build(),
                OrderLogModel.builder()
                        .orderId(ORDER_ID).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(EMPLOYEE_ID)
                        .previousStatus(OrderStatus.EN_PREPARACION.name()).newStatus(OrderStatus.ENTREGADO.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(10)).build(),
                // Order 43 — employee 5 — 30 min
                OrderLogModel.builder()
                        .orderId(secondOrderId).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .previousStatus(null).newStatus(OrderStatus.PENDIENTE.name())
                        .date(DATE_FIRST_TRANSITION).build(),
                OrderLogModel.builder()
                        .orderId(secondOrderId).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(secondEmployeeId)
                        .previousStatus(OrderStatus.PENDIENTE.name()).newStatus(OrderStatus.EN_PREPARACION.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(1)).build(),
                OrderLogModel.builder()
                        .orderId(secondOrderId).restaurantId(RESTAURANT_ID).customerId(CUSTOMER_ID)
                        .employeeId(secondEmployeeId)
                        .previousStatus(OrderStatus.EN_PREPARACION.name()).newStatus(OrderStatus.ENTREGADO.name())
                        .date(DATE_FIRST_TRANSITION.plusMinutes(30)).build()
        );
    }
}
