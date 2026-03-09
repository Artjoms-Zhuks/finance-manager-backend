package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Projection for obtaining the sum by category
    interface CategorySum {
        String getCategory();

        BigDecimal getAmount();
    }

    // Search by part description
    @Query("SELECT t FROM Transaction t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Transaction> searchByDescription(@Param("query") String query);

    // Total amount spent after a certain date
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.createdAt >= :startDate")
    BigDecimal sumAmountAfter(@Param("startDate") LocalDateTime startDate);

    // Grouping: Category + Amount after a specific date
    @Query("SELECT t.category as category, SUM(t.amount) as amount " +
            "FROM Transaction t " +
            "WHERE t.createdAt >= :startDate " +
            "GROUP BY t.category")
    List<CategorySum> getExpensesByCategoryAfter(@Param("startDate") LocalDateTime startDate);
}