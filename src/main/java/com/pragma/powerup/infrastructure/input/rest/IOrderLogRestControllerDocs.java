package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "order-log-rest-controller", description = "Endpoints for recording and querying the status change history of orders")
public interface IOrderLogRestControllerDocs {

    @Operation(
            summary = "Save order status log",
            description = "Records a status transition for an order. Called internally by micro-plazoleta each time an order moves to a new state (PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO / CANCELADO)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log entry saved successfully"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid fields in the request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "Missing orderId",
                                            value = "{\"message\":\"Validation failed\",\"errors\":[{\"field\":\"orderId\",\"message\":\"Order ID is required\"}]}"),
                                    @ExampleObject(name = "Missing newStatus",
                                            value = "{\"message\":\"Validation failed\",\"errors\":[{\"field\":\"newStatus\",\"message\":\"New status is required\"}]}")
                            })),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"No authentication token provided.\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"An unexpected error occurred. Please contact the administrator.\"}")))
    })
    ResponseEntity<Void> createOrderLog(OrderLogRequestDto orderLogRequestDto);

    @Operation(
            summary = "Get order traceability history",
            description = "Returns the full list of status transitions for a given order, ordered chronologically. The authenticated customer can only retrieve history for their own orders."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order history retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = OrderLogResponseDto.class)),
                            examples = @ExampleObject(
                                    name = "Two-entry history",
                                    value = "[" +
                                            "{\"id\":\"6672a1b3c4d5e6f7a8b9c0d1\",\"orderId\":42,\"restaurantId\":7,\"customerId\":15,\"employeeId\":null,\"previousStatus\":null,\"newStatus\":\"PENDIENTE\",\"date\":\"2024-06-22T10:00:00\"}," +
                                            "{\"id\":\"6672a1b3c4d5e6f7a8b9c0d2\",\"orderId\":42,\"restaurantId\":7,\"customerId\":15,\"employeeId\":3,\"previousStatus\":\"PENDIENTE\",\"newStatus\":\"EN_PREPARACION\",\"date\":\"2024-06-22T10:15:00\"}" +
                                            "]"
                            ))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"No authentication token provided.\"}"))),
            @ApiResponse(responseCode = "403", description = "The order does not belong to the authenticated customer",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"Access Denied\"}"))),
            @ApiResponse(responseCode = "404", description = "No traceability records found for the given order",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"Business validation failed\",\"errors\":[{\"field\":\"orderId\",\"message\":\"No traceability records found for the given order\"}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"An unexpected error occurred. Please contact the administrator.\"}")))
    })
    ResponseEntity<List<OrderLogResponseDto>> getOrderHistory(
            @Parameter(description = "ID of the order whose history is requested", required = true, example = "42")
            Long orderId);
}
