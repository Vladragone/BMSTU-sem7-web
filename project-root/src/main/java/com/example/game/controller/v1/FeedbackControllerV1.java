package com.example.game.controller.v1;

import com.example.game.model.Feedback;
import com.example.game.service.interfaces.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackControllerV1 {

    private final IFeedbackService feedbackService;

    public FeedbackControllerV1(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Добавить отзыв")
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

    @Operation(summary = "Получить список всех отзывов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отзывы успешно получены"),
            @ApiResponse(responseCode = "204", description = "Отзывы отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(feedbacks);
    }

    @Operation(summary = "Получить отзыв по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отзыв найден"),
            @ApiResponse(responseCode = "404", description = "Отзыв не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

}
