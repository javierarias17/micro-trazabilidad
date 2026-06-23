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
        OrderLogDocument document = orderLogDocumentMapper.toDocument(orderLogModel);
        orderLogMongoRepository.save(document);
    }

    @Override
    public List<OrderLogModel> findAllByOrderId(Long orderId) {
        List<OrderLogDocument> documents = orderLogMongoRepository.findAllByOrderId(orderId);
        return orderLogDocumentMapper.toModelList(documents);
    }
}
