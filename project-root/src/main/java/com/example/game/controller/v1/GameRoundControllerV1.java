package com.example.game.controller.v1;

import com.example.game.model.GameRound;
import com.example.game.model.GameSession;
import com.example.game.service.interfaces.IGameRoundService;
import com.example.game.service.interfaces.IGameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gamerounds")
public class GameRoundControllerV1 {

    private final IGameRoundService gameRoundService;
    private final IGameSessionService gameSessionService;

    public GameRoundControllerV1(IGameRoundService gameRoundService, IGameSessionService gameSessionService) {
        this.gameRoundService = gameRoundService;
        this.gameSessionService = gameSessionService;
    }

    @Operation(summary = "Создать новый раунд")
    @PostMapping
    public ResponseEntity<GameRound> createRound(@RequestBody GameRound round) {
        GameRound saved = gameRoundService.saveRound(round);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Получить все раунды по сессии")
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<GameRound>> getRoundsBySession(@PathVariable Long sessionId) {
        GameSession session = gameSessionService.getSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Сессия не найдена"));
        List<GameRound> rounds = gameRoundService.getRoundsBySession(session);
        if (rounds.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(rounds);
    }

    @Operation(summary = "Получить текущий раунд в сессии")
    @GetMapping("/session/{sessionId}/current")
    public ResponseEntity<GameRound> getCurrentRound(@PathVariable Long sessionId) {
        GameSession session = gameSessionService.getSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Сессия не найдена"));
        GameRound round = gameRoundService.getCurrentRound(session);
        if (round == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(round);
    }
}
