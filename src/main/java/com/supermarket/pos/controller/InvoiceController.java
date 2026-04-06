package com.supermarket.pos.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.repository.InvoiceRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // 🔥 GET ALL INVOICES
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}