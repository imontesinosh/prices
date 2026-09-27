package com.demo.prices.domain.repository;

import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;

import java.time.LocalDateTime;
import java.util.Optional;

public class PricesRepository {
    public Optional<Price> findOneWithHighestPriority(LocalDateTime liveDate, Product product, Brand brand) {
        return Optional.empty();
    }

    public Optional<Price> fastFindOneWithHighestPriority(LocalDateTime liveDate, Long productId, Long brandId) {
        return Optional.empty();
    }

}
