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
public class OrderEfficiencyModel {
    private Long orderId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long durationSeconds;
}
