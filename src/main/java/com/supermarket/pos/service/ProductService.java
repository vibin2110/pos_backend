package com.supermarket.pos.service;

import com.supermarket.pos.entity.Product;
import com.supermarket.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private LowStockEmailService lowStockEmailService;

    private static final String ADMIN_EMAIL = "admin@email.com";

    // Get product by barcode
    public Product getProductByBarcode(String barcode) {
        Optional<Product> product = repo.findByBarcode(barcode);
        return product.orElse(null);
    }

    // Search products by name (used in POS typing search)
    public List<Product> searchProductsByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // Save new product
    public Product saveProduct(Product product) {
        Product savedProduct = repo.save(product);

        if (savedProduct.getQuantity() <= savedProduct.getMinStock()) {
            lowStockEmailService.sendLowStockAlert(ADMIN_EMAIL, savedProduct);
        }

        return savedProduct;
    }

    // Update product
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = repo.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            product.setName(updatedProduct.getName());
            product.setBarcode(updatedProduct.getBarcode());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setMinStock(updatedProduct.getMinStock());

            Product savedProduct = repo.save(product);

            if (savedProduct.getQuantity() <= savedProduct.getMinStock()) {
                lowStockEmailService.sendLowStockAlert(ADMIN_EMAIL, savedProduct);
            }

            return savedProduct;
        }

        return null;
    }

    // Delete product
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    // Low stock products
    public List<Product> getLowStockProducts() {
        return repo.findLowStockProducts();
    }

    public void updateStock(Long id, int qtyToAdd) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setQuantity(product.getQuantity() + qtyToAdd);

        Product savedProduct = repo.save(product);

        if (savedProduct.getQuantity() <= savedProduct.getMinStock()) {
            lowStockEmailService.sendLowStockAlert(ADMIN_EMAIL, savedProduct);
        }
    }
}
