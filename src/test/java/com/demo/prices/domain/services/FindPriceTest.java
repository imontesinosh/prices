package com.demo.prices.domain.services;

import com.demo.prices.domain.Price;
import com.demo.prices.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindPriceTest {

    public static final LocalDateTime LIVE_DATE = LocalDateTime.now();
    public static final Long PRODUCT_ID = 1L;
    public static final Long RETAIL_CHAIN_ID = 11L;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    FindPrice findPrice;

    @Test
    void shouldFailWhenTheProductDoesNotExists(){
        assertThat(findPrice).isNotNull();

        when(productRepository.find(PRODUCT_ID)).thenReturn(Optional.empty());

        FindPriceResults price = findPrice.find(LIVE_DATE, PRODUCT_ID, RETAIL_CHAIN_ID);

        assertThat(price).isInstanceOf(ProductNotFound.class);

        assertThat( ((ProductNotFound)price).productId() ).isEqualTo(PRODUCT_ID);

    }

}
