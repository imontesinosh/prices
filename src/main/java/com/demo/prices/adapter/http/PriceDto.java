package com.demo.prices.adapter.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PriceDto {

    private Long productId;

    private Long brandId;

    private Long priceList;

    private LocalDateTime validFrom;

    private LocalDateTime validTp;

    private AmountDto amount;

}

