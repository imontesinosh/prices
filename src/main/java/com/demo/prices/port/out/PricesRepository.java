package com.demo.prices.port.out;

import com.demo.prices.adapter.db.PricesJpaRepository;
import com.demo.prices.adapter.db.entities.PricesJpaEntity;
import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PricesRepository {

    private final PricesJpaRepository pricesJpaRepository;
    private final PriceEntityToDomainMapper entityToDomainMapper;

    public Optional<Price> findOneWithHighestPriority(LocalDateTime liveDate, Product product, Brand brand) {
        List<PricesJpaEntity> matchingPrices = pricesJpaRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriorityDesc(liveDate, liveDate, product.getId(), brand.getId());
        if(matchingPrices.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(entityToDomainMapper.map(matchingPrices.getFirst()));
    }

    public Optional<Price> fastFindOneWithHighestPriority(LocalDateTime liveDate, Long productId, Long brandId) {
        return pricesJpaRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriority(liveDate, liveDate, productId, brandId)
                .map(it -> entityToDomainMapper.map(it));
    }

}
