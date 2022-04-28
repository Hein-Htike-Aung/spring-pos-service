package com.example.posservice.dto;

import com.example.posservice.enumeration.OrderStatus;
import com.example.posservice.model.Customer;
import com.example.posservice.model.OrderDetails;
import com.example.posservice.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponse {

    private Long id;
    private Instant deliveryDate;
    private Instant orderDate;
    private OrderStatus orderStatus;
    private CustomerDto customerDto;
    private EmployeeDto employeeDto;
    private PaymentDto payment;
    private String description;
    private List<OrderDetailsResponse> orderDetails;
}
