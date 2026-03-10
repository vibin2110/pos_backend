package com.supermarket.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supermarket.pos.entity.Invoice;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}