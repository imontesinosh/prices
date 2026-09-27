package com.demo.prices.domain.repository;

import com.demo.prices.domain.Product;

import java.util.Optional;

public class ProductRepository {
    public Optional<Product> find(Long productId) {
        return Optional.empty();
    }
}
