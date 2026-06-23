package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogMongoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderLogMongoAdapter implements IOrderLogPersistencePort {

    private final IOrderLogMongoRepository orderLogMongoRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;

    @Override
    public void save(OrderLogModel orderLogModel) {
        orderLogMongoRepository.save(orderLogDocumentMapper.toDocument(orderLogModel));
    }

    @Override
    public List<OrderLogModel> findAllByOrderId(Long orderId) {
        return orderLogDocumentMapper.toModelList(orderLogMongoRepository.findAllByOrderId(orderId));
    }

    @Override
    public List<OrderLogModel> findAllByRestaurantId(Long restaurantId) {
        List<OrderLogDocument> documents = orderLogMongoRepository.findAllByRestaurantId(restaurantId);
        return orderLogDocumentMapper.toModelList(documents);
    }
}
