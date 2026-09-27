package com.demo.prices.domain.services;

import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;
import com.demo.prices.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class FindPrice {

    private final ProductRepository productRepository;
    public FindPriceResults find(LocalDateTime liveDate, Long productId, long retailChainId) {

        Optional<Product> product = productRepository.find(productId);

        if(product.isEmpty()){
            return new ProductNotFound(productId);
        }

        return null;
    }
}
