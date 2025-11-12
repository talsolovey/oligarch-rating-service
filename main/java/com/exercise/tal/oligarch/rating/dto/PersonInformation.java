package com.exercise.tal.oligarch.rating.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonInformation(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {}
