package com.exercise.tal.oligarch.rating.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class OligarchHelperClient {
    private final RestClient helperClient;

    public OligarchHelperClient(RestClient.Builder helperClientBuilder, @Value("${oligarch-helper.baseUrl:http://oligarch-helper}") String baseUrl) {
        this.helperClient = helperClientBuilder.baseUrl(baseUrl).build();
    }

    public BigDecimal thresholdUSD() {
        return helperClient.get()
                .uri("/oligarch-threshold")
                .retrieve()
                .body(BigDecimal.class);
    }
}
