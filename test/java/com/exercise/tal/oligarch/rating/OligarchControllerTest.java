package com.exercise.tal.oligarch.rating;

import com.exercise.tal.oligarch.rating.dto.*;
import com.exercise.tal.oligarch.rating.model.Oligarch;
import com.exercise.tal.oligarch.rating.service.OligarchRatingService;
import com.exercise.tal.oligarch.rating.web.OligarchController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OligarchController.class)
public class OligarchControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockitoBean
    private OligarchRatingService service;

    @Test
    void rateReturnsCreatedWhenPersisted() throws Exception {
        String body = """
        {"id":123,
         "personInformation":{"firstName":"Bill","lastName":"Gates"},
         "financialAssets":{"cashAmount":100,"currency":"USD","bitcoinAmount":2}}
        """;
        Oligarch saved = new Oligarch(123L, "Bill", "Gates", BigDecimal.valueOf(60100));
        StatusResponse response = new StatusResponse(RatingStatus.OLIGARCH, BigDecimal.valueOf(60100), saved);
        given(service.getStatus(any(PersonData.class))).willReturn(response);

        mvc.perform(post("/oligarchs/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andExpect(status().isCreated());
    }

    @Test
    void rateReturnsOkWhenNotPersisted() throws Exception {
        String body = """
        {"id":123,
         "personInformation":{"firstName":"Bill","lastName":"Gates"},
         "financialAssets":{"cashAmount":100,"currency":"USD","bitcoinAmount":2}}
        """;
        StatusResponse response = new StatusResponse(RatingStatus.NOT_OLIGARCH, BigDecimal.valueOf(100), null);
        given(service.getStatus(any(PersonData.class))).willReturn(response);

        mvc.perform(post("/oligarchs/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andExpect(status().isOk());
    }
}
