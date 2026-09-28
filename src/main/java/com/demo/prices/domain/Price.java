package com.demo.prices.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class Price {

    private Brand brand;

    private Product product;

    private PriceList priceList;

    private LocalDateTime start;

    private LocalDateTime end;

    private Long priority;

    private MonetaryAmount amount;

}
