package com.example.game.controller;

import com.example.game.model.GameSession;
import com.example.game.service.interfaces.IGameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gamesessions")
public class GameSessionController {

    private final IGameSessionService gameSessionService;

    public GameSessionController(IGameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @Operation(summary = "Создать новую игровую сессию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Игровая сессия успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<GameSession> createGameSession(@RequestBody GameSession gameSession) {
        GameSession savedGameSession = gameSessionService.saveGameSession(gameSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameSession);
    }
}
