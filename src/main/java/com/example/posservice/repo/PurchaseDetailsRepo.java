package com.example.posservice.repo;

import com.example.posservice.model.OrderDetails;
import com.example.posservice.model.Product;
import com.example.posservice.model.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Long> {

    Optional<List<PurchaseDetails>> findByProductId(Long productId);

    Optional<List<PurchaseDetails>> findByPurchaseId(Long purchaseId);

    @Query(value = "SELECT pd FROM Purchase p " +
            "INNER JOIN PurchaseDetails pd ON p.id = pd.id " +
            "WHERE FUNCTION('MONTH', p.purchaseDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', p.purchaseDate) = FUNCTION('YEAR',CURRENT_DATE)")
    Optional<List<PurchaseDetails>> findCurrentMonthPurchaseDetails();

    @Query(value = "SELECT pd FROM Purchase p " +
            "INNER JOIN PurchaseDetails pd ON p.id = pd.id " +
            "WHERE FUNCTION('MONTH', p.purchaseDate) = FUNCTION('MONTH', :date) " +
            "AND FUNCTION('YEAR', p.purchaseDate) = FUNCTION('YEAR',CURRENT_DATE)")
    Optional<List<PurchaseDetails>> findPurchaseDetailsByMonth(@Param("date") Date date);

}
