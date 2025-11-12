package com.exercise.tal.oligarch.rating.service;

import com.exercise.tal.oligarch.rating.client.AssetsValuationClient;
import com.exercise.tal.oligarch.rating.client.OligarchHelperClient;
import com.exercise.tal.oligarch.rating.dto.*;
import com.exercise.tal.oligarch.rating.model.Oligarch;
import com.exercise.tal.oligarch.rating.repository.OligarchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OligarchRatingService {
    private final OligarchRepository oligarchRepository;
    private final AssetsValuationClient assetsValuationClient;
    private final OligarchHelperClient oligarchHelperClient;

    public OligarchRatingService(OligarchRepository oligarchRepository, AssetsValuationClient assetsValuationClient, OligarchHelperClient oligarchHelperClient) {
        this.oligarchRepository = oligarchRepository;
        this.assetsValuationClient = assetsValuationClient;
        this.oligarchHelperClient = oligarchHelperClient;
    }

    public StatusResponse getStatus(PersonData data) {
        FinancialAssets assets = data.financialAssets();

        BigDecimal cashUSD = assetsValuationClient.evaluateCashUSD(assets.cashAmount(), assets.currency());
        BigDecimal bitcoinUSD = assetsValuationClient.evaluateBitCoinUSD();
        BigDecimal assetsValue = cashUSD.add(bitcoinUSD.multiply(assets.bitcoinAmount()));
        BigDecimal threshold = oligarchHelperClient.thresholdUSD();

        if (assetsValue.compareTo(threshold) > 0) {
            PersonInformation info = data.personInformation();
            Oligarch savedOligarch = oligarchRepository.save(new Oligarch(data.id(), info.firstName(), info.lastName(), assetsValue));
            return new StatusResponse(RatingStatus.OLIGARCH, assetsValue, savedOligarch);
        } else {
            return new StatusResponse(RatingStatus.NOT_OLIGARCH, assetsValue, null);
        }
    }

    public List<Oligarch> get() {
        return oligarchRepository.findAll();
    }

    public Oligarch get(Long id) {
        return oligarchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Oligarch with id " + id + " not found"));
    }

    public long getRank(Long id) {
        Oligarch oligarch = oligarchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Oligarch with id " + id + " not found"));
        return oligarchRepository.findRankByAssetsValue(oligarch.getAssetsValue());
    }
}
