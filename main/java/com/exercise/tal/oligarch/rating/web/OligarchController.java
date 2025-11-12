package com.exercise.tal.oligarch.rating.web;

import com.exercise.tal.oligarch.rating.dto.PersonData;
import com.exercise.tal.oligarch.rating.dto.RatingStatus;
import com.exercise.tal.oligarch.rating.dto.StatusResponse;
import com.exercise.tal.oligarch.rating.model.Oligarch;
import com.exercise.tal.oligarch.rating.service.OligarchRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/oligarchs")
public class OligarchController {
    private final OligarchRatingService oligarchRatingService;

    public OligarchController(OligarchRatingService oligarchRatingService) {
        this.oligarchRatingService = oligarchRatingService;
    }

    @PostMapping("/rate")
    public ResponseEntity<StatusResponse> rateOligarch(@Valid @RequestBody PersonData data) {
        StatusResponse response = oligarchRatingService.getStatus(data);
        if (response.status() == RatingStatus.OLIGARCH) {
            return ResponseEntity.created(URI.create("/oligarchs/" + response.oligarch().getId())).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Oligarch>> getOligarchs() {
        return ResponseEntity.ok(oligarchRatingService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oligarch> getOligarchById(@PathVariable Long id) {
        return ResponseEntity.ok(oligarchRatingService.get(id));
    }

    @GetMapping("/{id}/rank")
    public ResponseEntity<Long> getOligarchRank(@PathVariable Long id) {
        return ResponseEntity.ok(oligarchRatingService.getRank(id));
    }
}
