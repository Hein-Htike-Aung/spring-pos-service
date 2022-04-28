package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.OrderDetails;
import com.example.posservice.repo.OrderDetailsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class OrderDetailsRepoProvider {
    private final OrderDetailsRepo orderDetailsRepo;

    public List<OrderDetails> findOrderDetailsListByProductId(Long productId) {
        return this.orderDetailsRepo.findByProductId(productId)
                .orElseThrow(() -> new SpringPosException("No Order Details List found with product Id - " + productId));
    }

    public List<OrderDetails> findByCustomerOrderId(Long id) {
        return this.orderDetailsRepo.findByCustomerOrderId(id)
                .orElseThrow(() -> new SpringPosException("No Order Details List found with orderId - " + id));
    }

    public List<OrderDetails> findCurrentMonthOrderDetails() {
        return this.orderDetailsRepo.findCurrentMonthOrderDetails()
                .orElseThrow(() -> new SpringPosException("There is no Incomes for this month"));

    }

    public List<OrderDetails> findOrderDetailsByMonth(Date date) {
        return this.orderDetailsRepo.findOrderDetailsByMonth(date).orElse(List.of());
    }
}
