package com.example.posservice.service;

import com.example.posservice.dto.OrderDetailsDto;
import com.example.posservice.dto.OrderDetailsResponse;
import com.example.posservice.dto.OrderRequest;
import com.example.posservice.dto.OrderResponse;
import com.example.posservice.enumeration.OrderStatus;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.CustomerMapper;
import com.example.posservice.mapper.OrderMapper;
import com.example.posservice.model.CustomerOrder;
import com.example.posservice.model.OrderDetails;
import com.example.posservice.model.Payment;
import com.example.posservice.model.Product;
import com.example.posservice.repo.*;
import com.example.posservice.repo.repo_provider.CustomerOrderRepoProvider;
import com.example.posservice.repo.repo_provider.EmployeeRepoProvider;
import com.example.posservice.repo.repo_provider.OrderDetailsRepoProvider;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerOrderService {
    private final CustomerOrderRepo customerOrderRepo;
    private final CustomerRepo customerRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final ProductRepoProvider productRepoProvider;
    private final ProductRepo productRepo;
    private final PaymentRepo paymentRepo;
    private final CustomerOrderRepoProvider customerOrderRepoProvider;
    private final OrderDetailsRepoProvider orderDetailsRepoProvider;
    private final EmployeeRepoProvider employeeRepoProvider;
    private final AppUserService appUserService;

    public void save(OrderRequest orderRequest) {

        // Save Customer
        this.customerRepo.save(this.customerMapper.mapToCustomer(orderRequest.getCustomerDto()));

        // Save Payment
        Payment savedPayment = this.paymentRepo.save(orderRequest.getPayment());

        orderRequest.setPayment(savedPayment);

        CustomerOrder savedOrder = this.customerOrderRepo
                .save(this.orderMapper.mapToCustomerOrder(orderRequest));

        // Save Customer Order
        this.orderDetailsRepo.saveAll(
                orderRequest.getOrderDetails().stream()
                        .filter(this::isQuantityValid)
                        .map(this.orderMapper::mapToOrderDetail)
                        .peek(orderDetails -> orderDetails.setCustomerOrder(savedOrder))
                        .collect(Collectors.toList())
        );

        // Save Order Details
        orderRequest.getOrderDetails().forEach(od -> {
            Product product = this.productRepoProvider.findById(od.getProductId());
            product.setQuantity(product.getQuantity() - od.getQuantity());

            this.productRepo.save(product);
        });

    }

    private boolean isQuantityValid(OrderDetailsDto orderDetailsDto) {

        Product saveProduct = this.productRepoProvider.findById(orderDetailsDto.getProductId());

        if (saveProduct.getQuantity() < orderDetailsDto.getQuantity()) {
            throw new SpringPosException("Requested Product Quantity is exceeded than the remaining quantity");
        }

        return true;
    }

    public void confirmOrder(Long orderId) {
        CustomerOrder targetOrder = this.customerOrderRepoProvider.findById(orderId);

        targetOrder.setDeliveryDate(Instant.now());
        targetOrder.setOrderStatus(OrderStatus.DELIVERED);
        targetOrder.setEmployee(this.employeeRepoProvider.findEmployeeByUserId(this.appUserService.getCurrentUser().getId()));
    }

    public List<OrderResponse> getAll() {
        return this.customerOrderRepo.findAll().stream()
                .map(this.orderMapper::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getById(Long id) {
        return this.orderMapper.mapToOrderResponse(this.customerOrderRepoProvider.findById(id));
    }

    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return this.customerOrderRepoProvider.findByCustomerId(customerId).stream()
                .map(this.orderMapper::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderDetailsResponse> getOrderDetailsForOrder(Long orderId) {
        return this.orderDetailsRepoProvider.findByCustomerOrderId(orderId).stream()
                .map(this.orderMapper::mapToOrderDetailsResponse)
                .collect(Collectors.toList());
    }

    public Long getCurrentMonthIncome() {

        long total;
        List<OrderDetails> monthlyOrderDetails = this.orderDetailsRepoProvider.findCurrentMonthOrderDetails();

        total = monthlyOrderDetails.stream()
                .mapToLong(od -> (long) (od.getProduct().getPrice() * od.getQuantity())).sum();

        return total;
    }

    public List<Long> getMonthlyIncomes() {

        String[] months = {
                "2022-01-01",
                "2022-02-01",
                "2022-03-01",
                "2022-04-01",
                "2022-05-01",
                "2022-06-01",
                "2022-07-01",
                "2022-08-01",
                "2022-09-01",
                "2022-10-01",
                "2022-11-01",
                "2022-12-01",
        };

        List<Long> monthlyIncomes = new ArrayList<>();

        Arrays.stream(months).forEach(month -> monthlyIncomes.add(
                this.orderDetailsRepoProvider.findOrderDetailsByMonth(Date.valueOf(month)).stream()
                        .mapToLong(od -> (long) (od.getProduct().getPrice() * od.getQuantity())).sum()
        ));


        return monthlyIncomes;
    }
}
