package com.supermarket.pos.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import com.supermarket.pos.entity.CartItem;
import com.supermarket.pos.entity.Product;
import com.supermarket.pos.entity.Invoice;
import com.supermarket.pos.entity.InvoiceItem;
import com.supermarket.pos.repository.InvoiceRepository;

@Service
public class BillingService {

    private final ProductService productService;
    private final InvoiceRepository invoiceRepository;

    // Cart stored in memory
    private Map<String, CartItem> cart = new HashMap<>();

    // Constructor Injection
    public BillingService(ProductService productService,
                          InvoiceRepository invoiceRepository) {
        this.productService = productService;
        this.invoiceRepository = invoiceRepository;
    }

    // Scan product using barcode
    public void scanProduct(String barcode) {

        Product product = productService.getProductByBarcode(barcode);

        if (cart.containsKey(barcode)) {
            cart.get(barcode).increaseQuantity();
        } else {
            cart.put(barcode, new CartItem(product, 1));
        }
    }

    // View cart
    public Collection<CartItem> getCartItems() {
        return cart.values();
    }

    // Calculate total
    public double getTotalAmount() {
        return cart.values()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    // Clear cart manually
    public void clearCart() {
        cart.clear();
    }

    // Checkout and save invoice
    public Invoice checkout() {

        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDateTime.now());

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cart.values()) {

            Product product = productService.getProductByBarcode(
                    cartItem.getProduct().getBarcode()
            );
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productService.saveProduct(product);

// LOW STOCK ALERT CHECK
            if (product.getQuantity() <= product.getMinStock()) {
                System.out.println("LOW STOCK ALERT: " + product.getName() +
                        " remaining: " + product.getQuantity());
            }
            InvoiceItem item = new InvoiceItem();
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setTotalPrice(cartItem.getTotalPrice());

            invoiceItems.add(item);
            total += cartItem.getTotalPrice();
        }


        invoice.setItems(invoiceItems);
        invoice.setTotalAmount(total);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Clear cart after successful checkout
        cart.clear();

        return savedInvoice;
    }
}