package com.exercise.tal.oligarch.rating.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PersonData(
        @NotNull Long id,
        @Valid @NotNull PersonInformation personInformation,
        @Valid @NotNull FinancialAssets financialAssets
) {}

