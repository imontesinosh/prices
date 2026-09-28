package com.demo.prices.port.in;

import com.demo.prices.domain.services.FindPrice;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PricesController {

    private final FindPrice findPrice;

}
