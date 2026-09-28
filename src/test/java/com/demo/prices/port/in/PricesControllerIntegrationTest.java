package com.demo.prices.port.in;

import com.demo.prices.adapter.db.PricesJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PricesControllerIntegrationTest {

    @Autowired
    private PricesJpaRepository pricesJpaRepository;

    MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldSucceedWhenThereAreFourPricesPreloaded() {
        assertThat(pricesJpaRepository).isNotNull();
        assertThat(pricesJpaRepository.findAll()).hasSize(4);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2020-06-14T10:00:00Z", "2020-06-14T21:00:00Z"})
    void shouldReturnTheFirstPrice(String liveDate) throws Exception{
        assertThat(pricesJpaRepository).isNotNull();
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("liveDate", liveDate)
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(hasProductId(35455))
                .andExpect(hasBrandId(1))
                .andExpect(hasPriceList(1))
                .andExpect(isValidFrom("2020-06-14T00:00:00"))
                .andExpect(isValidTo("2020-12-31T23:59:59"))
                .andExpect(hasCurrency("EUR"))
                .andExpect(hasAmount("35.5"));
    }

    @NotNull
    private static ResultMatcher hasProductId(int expectedValue) {
        return jsonPath("$.product_id").value(expectedValue);
    }

    @NotNull
    private static ResultMatcher hasBrandId(int expectedValue) {
        return jsonPath("$.brand_id").value(expectedValue);
    }

    @NotNull
    private static ResultMatcher hasPriceList(int expectedValue) {
        return jsonPath("$.price_list").value(expectedValue);
    }

    @NotNull
    private static ResultMatcher isValidFrom(String expectedValue1) {
        return jsonPath("$.valid_from").value(expectedValue1);
    }

    @NotNull
    private static ResultMatcher isValidTo(String expectedValue1) {
        return jsonPath("$.valid_tp").value(expectedValue1);
    }

    @NotNull
    private static ResultMatcher hasCurrency(String eur) {
        return jsonPath("$.amount.currency").value(eur);
    }

    @NotNull
    private static ResultMatcher hasAmount(String expectedValue2) {
        return jsonPath("$.amount.value").value(expectedValue2);
    }

    @Test
    void shouldReturnTheSecondPrice() throws Exception{
        assertThat(pricesJpaRepository).isNotNull();
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("liveDate", "2020-06-14T16:00:00Z")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(hasProductId(35455))
                .andExpect(hasBrandId(1))
                .andExpect(hasPriceList(2))
                .andExpect(isValidFrom("2020-06-14T15:00:00"))
                .andExpect(isValidTo("2020-06-14T18:30:00"))
                .andExpect(hasCurrency("EUR"))
                .andExpect(hasAmount("25.45"));
    }

    @Test
    void shouldReturnTheThirdPrice() throws Exception{
        assertThat(pricesJpaRepository).isNotNull();
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("liveDate", "2020-06-15T10:00:00Z")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(hasProductId(35455))
                .andExpect(hasBrandId(1))
                .andExpect(hasPriceList(3))
                .andExpect(isValidFrom("2020-06-15T00:00:00"))
                .andExpect(isValidTo("2020-06-15T11:00:00"))
                .andExpect(hasCurrency("EUR"))
                .andExpect(hasAmount("30.5"));
    }

    @Test
    void shouldReturnTheFourthPrice() throws Exception{
        assertThat(pricesJpaRepository).isNotNull();
        mockMvc.perform(get("/api/v1/prices")
                        .queryParam("liveDate", "2020-06-16T21:00:00Z")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(hasProductId(35455))
                .andExpect(hasBrandId(1))
                .andExpect(hasPriceList(4))
                .andExpect(isValidFrom("2020-06-15T16:00:00"))
                .andExpect(isValidTo("2020-12-31T23:59:59"))
                .andExpect(hasCurrency("EUR"))
                .andExpect(hasAmount("38.95"));
    }

}
