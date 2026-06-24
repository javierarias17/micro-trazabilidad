package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ICreateOrderLogServicePort;
import com.pragma.powerup.domain.api.IGetOrderEfficiencyServicePort;
import com.pragma.powerup.domain.api.IGetOrderHistoryServicePort;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IPlazoletaServicePort;
import com.pragma.powerup.domain.usecase.CreateOrderLogUseCase;
import com.pragma.powerup.domain.usecase.GetOrderEfficiencyUseCase;
import com.pragma.powerup.domain.usecase.GetOrderHistoryUseCase;
import com.pragma.powerup.infrastructure.out.http.adapter.PlazoletaServiceAdapter;
import com.pragma.powerup.infrastructure.out.http.client.IPlazoletaServiceFeignClient;
import com.pragma.powerup.infrastructure.out.mongo.adapter.OrderLogMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IOrderLogMongoRepository orderLogMongoRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;
    private final IAuthenticatedUserPort authenticatedUserPort;
    private final IPlazoletaServiceFeignClient plazoletaServiceFeignClient;

    @Bean
    public IPlazoletaServicePort plazoletaServicePort() {
        return new PlazoletaServiceAdapter(plazoletaServiceFeignClient);
    }

    @Bean
    public IOrderLogPersistencePort orderLogPersistencePort() {
        return new OrderLogMongoAdapter(orderLogMongoRepository, orderLogDocumentMapper);
    }

    @Bean
    public ICreateOrderLogServicePort createOrderLogServicePort() {
        return new CreateOrderLogUseCase(orderLogPersistencePort(), authenticatedUserPort);
    }

    @Bean
    public IGetOrderHistoryServicePort getOrderHistoryServicePort() {
        return new GetOrderHistoryUseCase(orderLogPersistencePort(), authenticatedUserPort);
    }

    @Bean
    public IGetOrderEfficiencyServicePort getOrderEfficiencyServicePort() {
        return new GetOrderEfficiencyUseCase(orderLogPersistencePort(), plazoletaServicePort());
    }
}
