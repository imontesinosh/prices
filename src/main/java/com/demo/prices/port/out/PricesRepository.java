package com.demo.prices.port.out;

import com.demo.prices.adapter.db.PricesJpaRepository;
import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class PricesRepository {

    private final PricesJpaRepository pricesJpaRepository;
    private final PriceEntityToDomainMapper entityToDomainMapper;

    public Optional<Price> findOneWithHighestPriority(LocalDateTime liveDate, Product product, Brand brand) {
        return pricesJpaRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndProductIdAndBrandIdOrderByPriority(liveDate, liveDate, product.getId(), brand.getId())
                .map(it -> entityToDomainMapper.map(it));
    }

    public Optional<Price> fastFindOneWithHighestPriority(LocalDateTime liveDate, Long productId, Long brandId) {
        return pricesJpaRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndProductIdAndBrandIdOrderByPriority(liveDate, liveDate, productId, brandId)
                .map(it -> entityToDomainMapper.map(it));
    }

}
