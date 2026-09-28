package com.demo.prices.port.out;

import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandRepository {
    public Optional<Brand> find(Long brandId) {
        // in the real world we should have a lookup in a db table
        return Optional.of(new Brand(brandId));
    }
}
