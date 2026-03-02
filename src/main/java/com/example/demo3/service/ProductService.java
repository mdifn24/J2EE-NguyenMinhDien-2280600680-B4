package com.example.demo3.service;

import com.example.demo3.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    public List<Product> getAll() {
        return products;
    }

    public Product getById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void save(Product product) {
        if (product.getId() == null) {
            product.setId(nextId++);
            products.add(product);
        } else {
            Product existing = getById(product.getId());
            if (existing != null) {
                existing.setName(product.getName());
                existing.setPrice(product.getPrice());
                existing.setCategory(product.getCategory());
                existing.setImage(product.getImage());
            }
        }
    }

    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}