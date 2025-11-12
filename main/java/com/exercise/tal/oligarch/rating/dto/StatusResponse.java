package com.exercise.tal.oligarch.rating.dto;

import com.exercise.tal.oligarch.rating.model.Oligarch;

import java.math.BigDecimal;

public record StatusResponse(
        RatingStatus status,
        BigDecimal assetsValue,
        Oligarch oligarch
) {}
