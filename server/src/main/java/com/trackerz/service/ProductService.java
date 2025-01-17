package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.ProductRepository;
import com.trackerz.model.Product;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ProductService {
    
    // Repository injection
    private final ProductRepository productRepository;

    // Constructor injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get a product by id
    public Product getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get products by name
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Update a product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProduct(id);
        // check if the product exists
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return productRepository.save(existingProduct);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}