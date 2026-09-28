package com.demo.prices.domain.services;

import com.demo.prices.domain.Brand;
import com.demo.prices.domain.Price;
import com.demo.prices.domain.PriceList;
import com.demo.prices.domain.Product;
import com.demo.prices.port.out.BrandRepository;
import com.demo.prices.port.out.PricesRepository;
import com.demo.prices.port.out.ProductRepository;
import com.demo.prices.domain.services.FindPriceResults.*;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FindPriceTest {

    public static final LocalDateTime LIVE_DATE = LocalDateTime.now();
    public static final Long PRODUCT_ID = 1L;
    public static final Long BRAND_ID = 11L;

    @Mock
    PricesRepository pricesRepository;
    @Mock
    BrandRepository brandRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    FindPrice findPrice;

    @Test
    void shouldFailWhenTheProductDoesNotExists(){
        assertThat(findPrice).isNotNull();

        when(productRepository.find(eq(PRODUCT_ID))).thenReturn(Optional.empty());

        FindPriceResults result = findPrice.find(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verify(productRepository).find(PRODUCT_ID);

        assertThat(result).isInstanceOf(ProductNotFound.class);

        assertThat( ((ProductNotFound)result).productId() ).isEqualTo(PRODUCT_ID);

    }

    @Test
    void shouldFailWhenTheBrandDoesNotExists(){
        assertThat(findPrice).isNotNull();

        when(productRepository.find(eq(PRODUCT_ID))).thenReturn(Optional.of(new Product(PRODUCT_ID)));
        when(brandRepository.find(eq(BRAND_ID))).thenReturn(Optional.empty());

        FindPriceResults result = findPrice.find(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verify(productRepository).find(PRODUCT_ID);
        verify(brandRepository).find(BRAND_ID);

        assertThat(result).isInstanceOf(BrandNotFound.class);
        assertThat( ((BrandNotFound)result).productId() ).isEqualTo(BRAND_ID);

    }

    @Test
    void shouldFailWhenThereIsNoPriceForLiveDateAndProductAndBrand(){
        assertThat(findPrice).isNotNull();

        when(productRepository.find(eq(PRODUCT_ID))).thenReturn(Optional.of(new Product(PRODUCT_ID)));
        when(brandRepository.find(eq(BRAND_ID))).thenReturn(Optional.of(new Brand(BRAND_ID)));
        when(pricesRepository.findOneWithHighestPriority(any(LocalDateTime.class), any(Product.class), any(Brand.class))).thenReturn(Optional.empty());

        FindPriceResults result = findPrice.find(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verify(productRepository).find(PRODUCT_ID);
        verify(brandRepository).find(BRAND_ID);
        verify(pricesRepository).findOneWithHighestPriority(any(), any(), any());

        assertThat(result).isInstanceOf(PriceNotFound.class);
        assertThat( ((PriceNotFound)result).product().getId() ).isEqualTo(PRODUCT_ID);
        assertThat( ((PriceNotFound)result).brand().getId() ).isEqualTo(BRAND_ID);

    }

    @Test
    void shouldFindThePriceWhenItExists(){
        assertThat(findPrice).isNotNull();

        when(productRepository.find(eq(PRODUCT_ID))).thenReturn(Optional.of(new Product(PRODUCT_ID)));
        when(brandRepository.find(eq(BRAND_ID))).thenReturn(Optional.of(new Brand(BRAND_ID)));
        Price value = tenEuros();
        when(pricesRepository.findOneWithHighestPriority(any(LocalDateTime.class), any(Product.class), any(Brand.class))).thenReturn(Optional.of(value));

        FindPriceResults result = findPrice.find(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verify(productRepository).find(PRODUCT_ID);
        verify(brandRepository).find(BRAND_ID);
        verify(pricesRepository).findOneWithHighestPriority(any(), any(), any());

        assertThat(result).isInstanceOf(Success.class);

    }

    private static Price tenEuros() {
        return new Price(new Brand(BRAND_ID), new Product(PRODUCT_ID), new PriceList(2L), LocalDateTime.of(1970, 1, 1, 0, 0), LocalDateTime.of(9999, 12, 31, 0, 0), 999L, Money.of(10, "EUR"));
    }

    @Test
    void shouldFailWhenFastFindDoesNotReturnResults(){
        assertThat(findPrice).isNotNull();

        when(pricesRepository.fastFindOneWithHighestPriority(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        FindPriceResults result = findPrice.fastFind(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verifyNoInteractions(productRepository);
        verifyNoInteractions(brandRepository);
        verify(pricesRepository).fastFindOneWithHighestPriority(any(), any(), any());

        assertThat(result).isInstanceOf(FastPriceNotFound.class);
        assertThat( ((FastPriceNotFound)result).liveDate() ).isEqualTo(LIVE_DATE);
        assertThat( ((FastPriceNotFound)result).productId() ).isEqualTo(PRODUCT_ID);
        assertThat( ((FastPriceNotFound)result).brandId() ).isEqualTo(BRAND_ID);

    }

    @Test
    void shouldSucceedWhenFastSearching(){
        assertThat(findPrice).isNotNull();

        when(pricesRepository.fastFindOneWithHighestPriority(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Optional.of(tenEuros()));

        FindPriceResults result = findPrice.fastFind(LIVE_DATE, PRODUCT_ID, BRAND_ID);

        verifyNoInteractions(productRepository);
        verifyNoInteractions(brandRepository);
        verify(pricesRepository).fastFindOneWithHighestPriority(any(), any(), any());

        assertThat(result).isInstanceOf(Success.class);

    }


}
