package com.example.posservice.mapper;

import com.example.posservice.dto.*;
import com.example.posservice.enumeration.OrderStatus;
import com.example.posservice.model.CustomerOrder;
import com.example.posservice.model.Employee;
import com.example.posservice.model.OrderDetails;
import com.example.posservice.repo.repo_provider.CustomerRepoProvider;
import com.example.posservice.repo.repo_provider.EmployeeRepoProvider;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import com.example.posservice.service.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
@Builder
public class OrderMapper {

    private final CustomerRepoProvider customerRepoProvider;
    private final ProductRepoProvider productRepoProvider;
    private final AppUserService appUserService;
    private final EmployeeRepoProvider employeeRepoProvider;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final CustomerMapper customerMapper;

    public CustomerOrder mapToCustomerOrder(OrderRequest orderRequest) {
        return CustomerOrder.builder()
                .id(orderRequest.getId())
                .customer(this.customerMapper.mapToCustomer(orderRequest.getCustomerDto()))
                .orderDate(Instant.now())
                .orderStatus(OrderStatus.PENDING)
                .description(orderRequest.getDescription())
                .payment(orderRequest.getPayment())
                .build();
    }

    public OrderDetails mapToOrderDetail(OrderDetailsDto orderDetailsDto) {
        return OrderDetails.builder()
                .id(orderDetailsDto.getId())
                .product(this.productRepoProvider.findById(orderDetailsDto.getProductId()))
                .quantity(orderDetailsDto.getQuantity())
                .build();
    }

    public OrderResponse mapToOrderResponse(CustomerOrder customerOrder) {

        Employee employee = customerOrder.getEmployee();
        return OrderResponse.builder()
                .id(customerOrder.getId())
                .deliveryDate(customerOrder.getDeliveryDate())
                .orderDate(customerOrder.getOrderDate())
                .description(customerOrder.getDescription())
                .orderStatus(customerOrder.getOrderStatus())
                .employeeDto(employee != null ? this.employeeService.getEmployeeDtoById(employee.getId()) : new EmployeeDto())
                .customerDto(this.customerService.findCustomerDtoById(customerOrder.getCustomer().getId()))
                .payment(this.paymentService.getPaymentDtoById(customerOrder.getPayment().getId()))
                .build();
    }

    public OrderDetailsResponse mapToOrderDetailsResponse(OrderDetails orderDetails) {
        return OrderDetailsResponse.builder()
                .id(orderDetails.getId())
                .quantity(orderDetails.getQuantity())
                .productResponse(productService.getProductDtoById(orderDetails.getProduct().getId()))
                .build();
    }
}
