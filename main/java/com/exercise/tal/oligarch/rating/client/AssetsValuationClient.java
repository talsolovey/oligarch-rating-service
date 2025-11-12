package com.exercise.tal.oligarch.rating.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;

@Component
public class AssetsValuationClient {
    private final RestClient assetsClient;

    public AssetsValuationClient(RestClient.Builder assetsClientBuilder, @Value("${assets-valuation.baseUrl:http://assets-valuation}") String baseUrl) {
        this.assetsClient = assetsClientBuilder.baseUrl(baseUrl).build();
    }

    public BigDecimal evaluateCashUSD(BigDecimal amount, String localCurrency) {
        return assetsClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cash/evaluate")
                        .queryParam("amount", amount)
                        .queryParam("localCurrency", localCurrency)
                        .build())
                .retrieve()
                .body(BigDecimal.class);
    }

    public BigDecimal evaluateBitCoinUSD() {
        return assetsClient.get()
                .uri("/bitcoin/value")
                .retrieve()
                .body(BigDecimal.class);
    }
}