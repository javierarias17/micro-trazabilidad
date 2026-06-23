package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLogModel {
    private String id;
    private Long orderId;
    private Long restaurantId;
    private Long customerId;
    private Long employeeId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime date;
}
