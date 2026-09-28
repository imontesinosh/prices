package com.demo.prices.domain.services;

import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;

import java.time.LocalDateTime;

sealed public interface FindPriceResults
        permits FindPriceResults.BrandNotFound,
        FindPriceResults.PriceNotFound,
        FindPriceResults.ProductNotFound,
        FindPriceResults.FastPriceNotFound,
        FindPriceResults.Success
{
    record ProductNotFound(Long productId) implements FindPriceResults { }
    record BrandNotFound(Long brandId) implements FindPriceResults { }
    record PriceNotFound(LocalDateTime liveDate, Product product, Brand brand) implements FindPriceResults { }
    record FastPriceNotFound(LocalDateTime liveDate, Long productId, Long brandId) implements FindPriceResults { }
    record Success(Price price) implements FindPriceResults { }

}
