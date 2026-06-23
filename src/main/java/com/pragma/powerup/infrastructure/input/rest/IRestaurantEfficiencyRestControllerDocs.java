package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.RestaurantEfficiencyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "restaurant-efficiency-rest-controller", description = "Endpoints for querying order efficiency metrics by restaurant")
public interface IRestaurantEfficiencyRestControllerDocs {

    @Operation(
            summary = "Get restaurant order efficiency",
            description = "Returns the duration of each completed order (from PENDIENTE to ENTREGADO) and a ranking of employees sorted by their average order duration (fastest first). Only accessible to users with OWNER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Efficiency report retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantEfficiencyResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"No authentication token provided.\"}"))),
            @ApiResponse(responseCode = "403", description = "Authenticated user does not have OWNER role",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"Access Denied\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"An unexpected error occurred. Please contact the administrator.\"}")))
    })
    ResponseEntity<RestaurantEfficiencyResponseDto> getOrderEfficiency(
            @Parameter(description = "ID of the restaurant to query", required = true, example = "7")
            Long restaurantId);
}
