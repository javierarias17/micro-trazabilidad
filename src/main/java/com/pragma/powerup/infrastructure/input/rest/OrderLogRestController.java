package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-logs")
@RequiredArgsConstructor
public class OrderLogRestController implements IOrderLogRestControllerDocs {

    private final IOrderLogHandler orderLogHandler;

    @Override
    @PostMapping
    public ResponseEntity<Void> createOrderLog(@Valid @RequestBody OrderLogRequestDto orderLogRequestDto) {
        orderLogHandler.createOrderLog(orderLogRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderLogResponseDto>> getOrderHistory(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderLogHandler.getOrderHistory(orderId));
    }
}
