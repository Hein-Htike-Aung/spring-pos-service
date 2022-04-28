package com.example.posservice.repo;

import com.example.posservice.model.OrderDetails;
import com.example.posservice.model.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

    Optional<List<OrderDetails>> findByProductId(Long productId);

    Optional<List<OrderDetails>> findByCustomerOrderId(Long id);

    @Query(value = "SELECT od FROM CustomerOrder co " +
            "INNER JOIN OrderDetails od ON co.id = od.id " +
            "WHERE FUNCTION('MONTH', co.deliveryDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', co.deliveryDate) = FUNCTION('YEAR',CURRENT_DATE)")
    Optional<List<OrderDetails>> findCurrentMonthOrderDetails();

    @Query(value = "SELECT od FROM CustomerOrder co " +
            "INNER JOIN OrderDetails od ON co.id = od.id " +
            "WHERE FUNCTION('MONTH', co.deliveryDate) = FUNCTION('MONTH', :date) " +
            "AND FUNCTION('YEAR', co.deliveryDate) = FUNCTION('YEAR',CURRENT_DATE)")
    Optional<List<OrderDetails>> findOrderDetailsByMonth(@Param("date") Date date);
}
