package com.supermarket.pos.controller;

import com.supermarket.pos.entity.Product;
import com.supermarket.pos.repository.ProductRepository;
import com.supermarket.pos.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    public ProductController(ProductRepository productRepository,
                             ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // Get product by barcode
    @GetMapping("/barcode/{barcode}")
    public Product getProductByBarcode(@PathVariable("barcode") String barcode) {

        Optional<Product> product = productRepository.findByBarcode(barcode);
        return product.orElse(null);
    }

    // Search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("name") String name) {

        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // Add product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        return productRepository.save(product);
    }

    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {

        Optional<Product> existing = productRepository.findById(id);

        if (existing.isPresent()) {

            Product p = existing.get();

            p.setName(product.getName());
            p.setBarcode(product.getBarcode());
            p.setPrice(product.getPrice());
            p.setQuantity(product.getQuantity());
            p.setMinStock(product.getMinStock());

            return productRepository.save(p);
        }

        return null;
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {

        productRepository.deleteById(id);
    }

    // Low stock products
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {

        return productRepository.findLowStockProducts();
    }

    @PutMapping("/update-stock")
    public void updateStock(@RequestBody Map<String, Object> data) {

        Long id = Long.valueOf(data.get("id").toString());
        int qtyToAdd = ((Number) data.get("quantity")).intValue();

        productService.updateStock(id, qtyToAdd);
    }
}