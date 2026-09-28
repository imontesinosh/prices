package com.demo.prices.adapter;

import com.demo.prices.adapter.http.AmountDto;
import com.demo.prices.adapter.http.PriceDto;
import com.demo.prices.domain.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceDomainToHttpMapper {

    public PriceDto map(Price price){
        return new PriceDto(
                price.getProduct().getId(),
                price.getBrand().getId(),
                price.getPriceList().getId(),
                price.getStart(),
                price.getEnd(),
                new AmountDto( price.getAmount().getNumber().toString(), price.getAmount().getCurrency().getCurrencyCode() )
        );
    }

}
