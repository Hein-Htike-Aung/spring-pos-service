package com.example.posservice.controller;

import com.example.posservice.dto.*;
import com.example.posservice.service.CustomerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private final CustomerOrderService customerOrderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody OrderRequest orderRequest
    ) {
        this.customerOrderService.save(orderRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/confirm/{orderId}")
    public ResponseEntity<ApiResponse> confirmOrder(
            @PathVariable("orderId") Long orderId
    ) {
        this.customerOrderService.confirmOrder(orderId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully confirmed Order")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerOrderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerOrderService.getById(id));
    }

    @GetMapping("/order-details/by-order/{id}")
    public ResponseEntity<List<OrderDetailsResponse>> getOrderDetailsForOrder(
            @PathVariable("id") Long orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerOrderService.getOrderDetailsForOrder(orderId));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersForCustomer(
            @PathVariable("customerId") Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.customerOrderService.getOrdersByCustomerId(customerId));
    }
}
