package com.example.game.controller;

import com.example.game.model.GameSession;
import com.example.game.service.interfaces.IGameSessionService;
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

    @PostMapping
    public ResponseEntity<GameSession> createGameSession(@RequestBody GameSession gameSession) {
        GameSession savedGameSession = gameSessionService.saveGameSession(gameSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameSession);
    }
}
