package com.example.game.controller;

import com.example.game.model.GameError;
import com.example.game.service.interfaces.IGameErrorService;
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

    @GetMapping
    public ResponseEntity<List<GameError>> getAllGameErrors() {
        return ResponseEntity.ok(gameErrorService.getAllGameErrors());
    }
}
