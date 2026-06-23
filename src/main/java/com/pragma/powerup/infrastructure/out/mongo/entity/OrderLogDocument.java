package com.pragma.powerup.infrastructure.out.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "order_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLogDocument {

    @Id
    private String id;

    @Field("order_id")
    private Long orderId;

    @Field("restaurant_id")
    private Long restaurantId;

    @Field("customer_id")
    private Long customerId;

    @Field("employee_id")
    private Long employeeId;

    @Field("previous_status")
    private String previousStatus;

    @Field("new_status")
    private String newStatus;

    private LocalDateTime date;
}
