package com.demo.prices.adapter.http;

import com.demo.prices.adapter.PriceDomainToHttpMapper;
import com.demo.prices.domain.services.FindPriceResults;
import com.demo.prices.domain.services.FindPriceResults.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPriceResultToHttpMapper {

    private final PriceDomainToHttpMapper priceDomainToHttpMapper;

    public ResponseEntity<?> toHttpResponse(FindPriceResults results){
        return switch(results){
            case ProductNotFound it -> ResponseEntity.badRequest().body(new HttpErrorResponseDto("The product " + it.productId() + "was not found"));
            case BrandNotFound it -> ResponseEntity.badRequest().body(new HttpErrorResponseDto("The brand " + it.brandId() + "was not found"));
            case PriceNotFound it -> ResponseEntity.badRequest().body(new HttpErrorResponseDto("There is no price for the requested product " + it.product().getId() + " and brand " + it.brand().getId()));
            case FastPriceNotFound it -> ResponseEntity.badRequest().body(new HttpErrorResponseDto("There is no price for the requested product "  + it.productId() + " and brand " + it.brandId()));
            case Success it -> ResponseEntity.ok().body(priceDomainToHttpMapper.map(it.price()));
        };
    }

}
