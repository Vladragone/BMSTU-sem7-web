package com.example.game.controller.v1;

import com.example.game.model.GameSession;
import com.example.game.service.interfaces.IGameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gamesessions")
public class GameSessionControllerV1 {

    private final IGameSessionService gameSessionService;

    public GameSessionControllerV1(IGameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @Operation(summary = "Создать новую игровую сессию")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Игровая сессия успешно создана"),
            @ApiResponse(responseCode = "500", description = "Ошибка при сохранении сессии")
    })
    @PostMapping
    public ResponseEntity<GameSession> createGameSession(@RequestBody GameSession gameSession) {
        GameSession saved = gameSessionService.saveGameSession(gameSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Получить все сессии пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сессии успешно получены"),
            @ApiResponse(responseCode = "204", description = "Сессий нет")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameSession>> getUserSessions(@PathVariable Long userId) {
        List<GameSession> sessions = gameSessionService.getSessionsByUser(userId);
        if (sessions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sessions);
    }

    @Operation(summary = "Получить сессию по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сессия найдена"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameSession> getSessionById(@PathVariable Long id) {
        return gameSessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить игровую сессию")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Сессия удалена"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        gameSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
