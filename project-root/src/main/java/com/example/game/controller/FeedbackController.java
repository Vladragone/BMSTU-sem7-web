package com.example.game.controller;

import com.example.game.model.Feedback;
import com.example.game.service.interfaces.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    private final IFeedbackService feedbackService;

    public FeedbackController(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Добавить отзыв/обратную связь")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отзыв успешно сохранён"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
    }
}
