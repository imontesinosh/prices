package com.demo.prices.adapter.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HttpErrorResponseDto {

    private String code;

    private String message;

}
