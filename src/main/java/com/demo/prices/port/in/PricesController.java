package com.demo.prices.port.in;

import com.demo.prices.adapter.http.FindPriceResultToHttpMapper;
import com.demo.prices.adapter.http.HttpErrorResponseDto;
import com.demo.prices.domain.services.FindPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class PricesController {

    private final FindPrice findPrice;
    private final FindPriceResultToHttpMapper resultMapper;

    @GetMapping("/api/v1/prices")
    public ResponseEntity<?> getPrice(@RequestParam("liveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime liveDate,
                                      @RequestParam("productId") Long productId,
                                      @RequestParam("brandId") Long brandId){
        return resultMapper.toHttpResponse(findPrice.find(liveDate, productId, brandId));
    }

    @GetMapping("/api/v1/fast-prices")
    public ResponseEntity<?> fastGetPrice(@RequestParam("liveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime liveDate,
                                      @RequestParam("productId") Long productId,
                                      @RequestParam("brandId") Long brandId){
        return resultMapper.toHttpResponse(findPrice.fastFind(liveDate, productId, brandId));
    }

}
