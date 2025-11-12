package com.exercise.tal.oligarch.rating;

import com.exercise.tal.oligarch.rating.client.*;
import com.exercise.tal.oligarch.rating.dto.*;
import com.exercise.tal.oligarch.rating.model.Oligarch;
import com.exercise.tal.oligarch.rating.repository.OligarchRepository;
import com.exercise.tal.oligarch.rating.service.OligarchRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class OligarchRatingServiceTest {
    private OligarchRatingService oligarchRatingService;
    private OligarchRepository repository;
    private AssetsValuationClient assetsValuationClient;
    private OligarchHelperClient oligarchHelperClient;

    @BeforeEach
    void setup() {
        repository = mock(OligarchRepository.class);
        assetsValuationClient = mock(AssetsValuationClient.class);
        oligarchHelperClient = mock(OligarchHelperClient.class);
        oligarchRatingService = new OligarchRatingService(repository, assetsValuationClient, oligarchHelperClient);
    }

    @Test
    void getStatusPersistsWhenAboveThreshold() {
        when(assetsValuationClient.evaluateCashUSD(BigDecimal.valueOf(100), "USD")).thenReturn(BigDecimal.valueOf(100));
        when(assetsValuationClient.evaluateBitCoinUSD()).thenReturn(BigDecimal.valueOf(30000));
        when(oligarchHelperClient.thresholdUSD()).thenReturn(BigDecimal.valueOf(10));

        StatusResponse response = oligarchRatingService.getStatus(new PersonData(
                1L,
                new PersonInformation("Bill", "Gates"),
                new FinancialAssets(BigDecimal.valueOf(100), "USD", BigDecimal.valueOf(2)))
        );

        assertThat(response.status()).isEqualTo(RatingStatus.OLIGARCH);
        assertThat(response.assetsValue()).isEqualTo(BigDecimal.valueOf(60100));

        ArgumentCaptor<Oligarch> captor = ArgumentCaptor.forClass(Oligarch.class);
        verify(repository).save(captor.capture());
        Oligarch savedOligarch = captor.getValue();
        assertThat(savedOligarch.getId()).isEqualTo(1L);
        assertThat(savedOligarch.getFirstName()).isEqualTo("Bill");
        assertThat(savedOligarch.getLastName()).isEqualTo("Gates");
        assertThat(savedOligarch.getAssetsValue()).isEqualTo(BigDecimal.valueOf(60100));
    }

    @Test
    void testNotPersistedWhenBelowThreshold() {
        when(assetsValuationClient.evaluateCashUSD(BigDecimal.valueOf(100), "USD")).thenReturn(BigDecimal.valueOf(100));
        when(assetsValuationClient.evaluateBitCoinUSD()).thenReturn(BigDecimal.valueOf(100));
        when(oligarchHelperClient.thresholdUSD()).thenReturn(BigDecimal.valueOf(100000));

        StatusResponse response = oligarchRatingService.getStatus(new PersonData(
                1L,
                new PersonInformation("Bill", "Gates"),
                new FinancialAssets(BigDecimal.valueOf(100), "USD", BigDecimal.valueOf(5))
                )
        );

        assertThat(response.status()).isEqualTo(RatingStatus.NOT_OLIGARCH);
        assertThat(response.oligarch()).isNull();
        verify(repository, never()).save(any());
    }
}
