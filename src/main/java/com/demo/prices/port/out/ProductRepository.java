package com.demo.prices.port.out;

import com.demo.prices.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductRepository {
    public Optional<Product> find(Long productId) {
        //In the real world we should have a lookup on a d table
        return Optional.of(new Product(productId));
    }
}
