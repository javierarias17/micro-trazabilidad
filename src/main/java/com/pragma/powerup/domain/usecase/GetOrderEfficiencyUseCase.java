package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IGetOrderEfficiencyServicePort;
import com.pragma.powerup.domain.common.DomainConstants;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.EmployeeRankingModel;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.model.RestaurantEfficiencyModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IPlazoletaServicePort;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetOrderEfficiencyUseCase implements IGetOrderEfficiencyServicePort {

    private static final double DEFAULT_AVERAGE_DURATION = 0.0;

    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IAuthenticatedUserPort authenticatedUserPort;
    private final IPlazoletaServicePort plazoletaServicePort;

    public GetOrderEfficiencyUseCase(IOrderLogPersistencePort orderLogPersistencePort,
                                     IAuthenticatedUserPort authenticatedUserPort,
                                     IPlazoletaServicePort plazoletaServicePort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
        this.plazoletaServicePort = plazoletaServicePort;
    }

    @Override
    public RestaurantEfficiencyModel getOrderEfficiency(Long restaurantId) {
        if (!DomainConstants.ROLE_OWNER.equals(authenticatedUserPort.getAuthenticatedUserRole())) {
            throw new ForbiddenException();
        }
        if (!plazoletaServicePort.isOwnerOfRestaurant(restaurantId)) {
            throw new ForbiddenException();
        }

        List<OrderLogModel> allLogs = orderLogPersistencePort.findAllByRestaurantId(restaurantId);

        Map<Long, List<OrderLogModel>> logsByOrder = allLogs.stream()
                .collect(Collectors.groupingBy(OrderLogModel::getOrderId));

        List<OrderEfficiencyModel> ordersEfficiency = new ArrayList<>();
        Map<Long, List<Long>> durationsByEmployee = new HashMap<>();

        for (Map.Entry<Long, List<OrderLogModel>> entry : logsByOrder.entrySet()) {
            List<OrderLogModel> orderLogs = entry.getValue();

            Optional<OrderLogModel> startLog = orderLogs.stream()
                    .filter(l -> OrderStatus.PENDIENTE.name().equals(l.getNewStatus()))
                    .min(Comparator.comparing(OrderLogModel::getDate));

            Optional<OrderLogModel> endLog = orderLogs.stream()
                    .filter(l -> OrderStatus.ENTREGADO.name().equals(l.getNewStatus()))
                    .max(Comparator.comparing(OrderLogModel::getDate));

            if (startLog.isEmpty() || endLog.isEmpty()) {
                continue;
            }

            long durationSeconds = ChronoUnit.SECONDS.between(
                    startLog.get().getDate(), endLog.get().getDate());

            ordersEfficiency.add(OrderEfficiencyModel.builder()
                    .orderId(entry.getKey())
                    .startDate(startLog.get().getDate())
                    .endDate(endLog.get().getDate())
                    .durationSeconds(durationSeconds)
                    .build());

            orderLogs.stream()
                    .filter(l -> OrderStatus.EN_PREPARACION.name().equals(l.getNewStatus())
                            && l.getEmployeeId() != null)
                    .findFirst()
                    .ifPresent(l -> durationsByEmployee
                            .computeIfAbsent(l.getEmployeeId(), k -> new ArrayList<>())
                            .add(durationSeconds));
        }

        List<EmployeeRankingModel> employeesRanking = durationsByEmployee.entrySet().stream()
                .map(e -> {
                    double avg = e.getValue().stream().mapToLong(Long::longValue).average().orElse(DEFAULT_AVERAGE_DURATION);
                    return EmployeeRankingModel.builder()
                            .employeeId(e.getKey())
                            .averageDurationSeconds(avg)
                            .totalOrders(e.getValue().size())
                            .build();
                })
                .sorted(Comparator.comparing(EmployeeRankingModel::getAverageDurationSeconds)).toList();

        return RestaurantEfficiencyModel.builder()
                .ordersEfficiency(ordersEfficiency)
                .employeesRanking(employeesRanking)
                .build();
    }
}
