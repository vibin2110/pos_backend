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
    private final LowStockEmailService lowStockEmailService;
    private static final String ADMIN_EMAIL = "vibinav784@gmail.com";
    private Map<String, CartItem> cart = new HashMap<>();
    public BillingService(ProductService productService,
                          InvoiceRepository invoiceRepository,
                          LowStockEmailService lowStockEmailService) {
        this.productService = productService;
        this.invoiceRepository = invoiceRepository;
        this.lowStockEmailService = lowStockEmailService;
    }

    public void scan(String barcode, int quantity) {
        Product product = productService.getProductByBarcode(barcode);

        if (product == null) {
            throw new RuntimeException("Product not found for barcode: " + barcode);
        }

        if (cart.containsKey(barcode)) {
            cart.get(barcode).increaseQuantity(quantity);
        } else {
            cart.put(barcode, new CartItem(product, quantity));
        }
    }

    public void addProduct(String barcode, int qty) {
        Product product = productService.getProductByBarcode(barcode);

        if (product == null) {
            throw new RuntimeException("Product not found for barcode: " + barcode);
        }

        if (qty <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        if (cart.containsKey(barcode)) {
            CartItem item = cart.get(barcode);
            item.setQuantity(qty);
        } else {
            cart.put(barcode, new CartItem(product, qty));
        }
    }

    public Collection<CartItem> getCartItems() {
        return cart.values();
    }

    public double getTotalAmount() {
        return cart.values()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public void clearCart() {
        cart.clear();
    }

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

            if (product == null) {
                throw new RuntimeException("Product not found during checkout");
            }

            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            product.setQuantity(product.getQuantity() - cartItem.getQuantity());

            if (product.getQuantity() <= product.getMinStock() && !product.isLowStockAlertSent()) {
                lowStockEmailService.sendLowStockAlert(ADMIN_EMAIL, product);
                product.setLowStockAlertSent(true);
            }

            if (product.getQuantity() > product.getMinStock() && product.isLowStockAlertSent()) {
                product.setLowStockAlertSent(false);
            }

            Product savedProduct = productService.saveProduct(product);

            InvoiceItem item = new InvoiceItem();
            item.setProductName(savedProduct.getName());
            item.setPrice(savedProduct.getPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setTotalPrice(cartItem.getTotalPrice());

            invoiceItems.add(item);
            total += cartItem.getTotalPrice();
        }

        invoice.setItems(invoiceItems);
        invoice.setTotalAmount(total);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        cart.clear();

        return savedInvoice;
    }

    public Map<String, Integer> getTopProducts() {
        List<Invoice> invoices = invoiceRepository.findAll();
        Map<String, Integer> productSales = new HashMap<>();

        for (Invoice invoice : invoices) {
            if (invoice.getItems() != null) {
                for (InvoiceItem item : invoice.getItems()) {
                    String name = item.getProductName();
                    productSales.put(
                            name,
                            productSales.getOrDefault(name, 0) + item.getQuantity()
                    );
                }
            }
        }

        return productSales;
    }
}
