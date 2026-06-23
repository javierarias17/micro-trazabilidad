package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IOrderLogMongoRepository extends MongoRepository<OrderLogDocument, String> {
    List<OrderLogDocument> findAllByOrderId(Long orderId);
}
