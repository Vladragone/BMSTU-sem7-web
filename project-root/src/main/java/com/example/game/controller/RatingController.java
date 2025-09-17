package com.example.game.controller;

import com.example.game.dto.RatingResponse;
import com.example.game.service.interfaces.IRatingService;
import com.example.game.util.JwtParserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/top")
    public ResponseEntity<RatingResponse> getTopAndRank(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "points") String sortBy) {
        
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        RatingResponse result = ratingService.getSortedRatingAndRank(token, sortBy);
        return ResponseEntity.ok(result);
    }
}