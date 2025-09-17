package com.example.game.controller;

import com.example.game.model.GameSession;
import com.example.game.service.interfaces.IGameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameSessionController {

    private final IGameSessionService gameSessionService;

    @Autowired
    public GameSessionController(IGameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/save-game")
    public ResponseEntity<GameSession> saveGameSession(@RequestBody GameSession gameSession) {
        try {
            GameSession savedGameSession = gameSessionService.saveGameSession(gameSession);
            return ResponseEntity.ok(savedGameSession);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
