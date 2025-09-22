package com.example.game.controller;

import com.example.game.dto.RatingResponse;
import com.example.game.service.interfaces.IRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<RatingResponse> getRatings(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false, defaultValue = "points") String sortBy,
            @RequestParam(required = false, defaultValue = "3") int limit) {

        RatingResponse result = ratingService.getSortedRatingAndRank(token, sortBy, limit);
        return ResponseEntity.ok(result);
    }
}
