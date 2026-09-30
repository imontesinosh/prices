package com.demo.prices.port.out;

import com.demo.prices.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductRepository {
    public Optional<Product> find(Long productId) {
        //In the real world we should have a lookup on a d table
        //instead we're going to mock that
        if(productId < 100){
            return Optional.empty();
        }
        return Optional.of(new Product(productId));
    }
}
