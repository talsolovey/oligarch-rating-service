package com.exercise.tal.oligarch.rating.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record FinancialAssets(
        @NotNull
        BigDecimal cashAmount,
        @NotBlank
        String currency,
        @NotNull
        BigDecimal bitcoinAmount
) {}
