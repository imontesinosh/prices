package com.demo.prices.port.out;

import com.demo.prices.adapter.db.entities.PricesJpaEntity;
import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.PriceList;
import com.demo.prices.domain.Product;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

@Component
public class PriceEntityToDomainMapper {
    public Price map(PricesJpaEntity it) {
        return new Price(
                new Brand(it.getBrandId()),
                new Product(it.getProductId()),
                new PriceList(it.getPriceList()),
                it.getStartDate(),
                it.getEndDate(),
                it.getPriority(),
                Money.of(it.getPrice(), it.getCurrency())
        );
    }
}
