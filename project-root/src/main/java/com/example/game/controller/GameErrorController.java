package com.example.game.controller;

import com.example.game.model.GameError;
import com.example.game.service.interfaces.IGameErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game-errors")
public class GameErrorController {

    private final IGameErrorService gameErrorService;

    @Autowired
    public GameErrorController(IGameErrorService gameErrorService) {
        this.gameErrorService = gameErrorService;
    }

    @GetMapping
    public List<GameError> getAllGameErrors() {
        return gameErrorService.getAllGameErrors();
    }
}
