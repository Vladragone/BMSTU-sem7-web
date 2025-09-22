package com.example.game.controller;

import com.example.game.dto.RatingResponse;
import com.example.game.service.interfaces.IRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Получить рейтинг игроков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рейтинг успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "401", description = "Неавторизован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден в рейтинге"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<RatingResponse> getRatings(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(required = false, defaultValue = "points") String sortBy,
            @RequestParam(required = false, defaultValue = "3") int limit) {

        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        RatingResponse result = ratingService.getSortedRatingAndRank(token, sortBy, limit);
        return ResponseEntity.ok(result);
    }
}
