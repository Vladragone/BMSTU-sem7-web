package com.example.game.controller;

import com.example.game.model.GameError;
import com.example.game.service.interfaces.IGameErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game-errors")
public class GameErrorController {

    private final IGameErrorService gameErrorService;

    public GameErrorController(IGameErrorService gameErrorService) {
        this.gameErrorService = gameErrorService;
    }

    @Operation(summary = "Получить список ошибок игры")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ошибки успешно получены"),
            @ApiResponse(responseCode = "204", description = "Ошибки отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<GameError>> getAllGameErrors() {
        List<GameError> errors = gameErrorService.getAllGameErrors();
        if (errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(errors);
    }
}
