package com.demo.prices.domain.services;

import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.Product;
import com.demo.prices.port.out.BrandRepository;
import com.demo.prices.port.out.PricesRepository;
import com.demo.prices.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.demo.prices.domain.services.FindPriceResults.*;

@RequiredArgsConstructor
@Service
public class FindPrice {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final PricesRepository pricesRepository;
    public FindPriceResults find(LocalDateTime liveDate, Long productId, Long brandId) {

        Optional<Product> maybeProduct = productRepository.find(productId);

        if(maybeProduct.isEmpty()){
            return new ProductNotFound(productId);
        }

        Optional<Brand> maybeBrand = brandRepository.find(brandId);

        if(maybeBrand.isEmpty()){
            return new BrandNotFound(brandId);
        }

        Product product = maybeProduct.get();
        Brand brand = maybeBrand.get();

        Optional<Price> maybePrice = pricesRepository.findOneWithHighestPriority(liveDate, product, brand);

        if(maybePrice.isEmpty()){
            return new PriceNotFound(liveDate, product, brand);
        }

        return new Success(maybePrice.get());
    }

    //This one makes 2 database trips less than the other method, bit at the cost of not knowing why the search failed
    public FindPriceResults fastFind(LocalDateTime liveDate, Long productId, Long brandId) {

        Optional<Price> maybePrice = pricesRepository.fastFindOneWithHighestPriority(liveDate, productId, brandId);

        if(maybePrice.isEmpty()){
            return new FastPriceNotFound(liveDate, productId, brandId);
        }

        return new Success(maybePrice.get());
    }


}
