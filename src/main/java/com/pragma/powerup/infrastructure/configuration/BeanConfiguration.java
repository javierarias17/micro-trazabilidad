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
import com.pragma.powerup.infrastructure.out.mongo.adapter.OrderLogMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IOrderLogMongoRepository orderLogMongoRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;
    private final IAuthenticatedUserPort authenticatedUserPort;

    @Value("${adapter.micro-plazoleta.url}")
    private String plazoletaServiceUrl;

    @Value("${adapter.micro-plazoleta.timeout}")
    private int plazoletaServiceTimeout;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(plazoletaServiceTimeout);
        factory.setReadTimeout(plazoletaServiceTimeout);
        return new RestTemplate(factory);
    }

    @Bean
    public IPlazoletaServicePort plazoletaServicePort() {
        return new PlazoletaServiceAdapter(restTemplate(), plazoletaServiceUrl);
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
        return new GetOrderEfficiencyUseCase(orderLogPersistencePort(), authenticatedUserPort, plazoletaServicePort());
    }
}
