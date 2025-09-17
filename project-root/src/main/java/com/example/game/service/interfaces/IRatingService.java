package com.example.game.service.interfaces;

import com.example.game.dto.RatingResponse;

public interface IRatingService {
    RatingResponse getSortedRatingAndRank(String token, String sortBy);
}