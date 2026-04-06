package com.supermarket.pos.repository;

import com.supermarket.pos.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Today's total sales
    @Query("""
        SELECT COALESCE(SUM(i.totalAmount),0)
        FROM Invoice i
        WHERE FUNCTION('DATE', i.createdAt) = CURRENT_DATE
    """)
    double getTodaySales();

    // Number of bills today
    @Query("""
        SELECT COUNT(i)
        FROM Invoice i
        WHERE FUNCTION('DATE', i.createdAt) = CURRENT_DATE
    """)
    int countTodayBills();
    @Query("""
        SELECT FUNCTION('DATE', i.createdAt), SUM(i.totalAmount)
        FROM Invoice i
        GROUP BY FUNCTION('DATE', i.createdAt)
        ORDER BY FUNCTION('DATE', i.createdAt)
    """)
    List<Object[]> getDailySales();
}