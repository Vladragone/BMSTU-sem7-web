package com.example.game.service;

import com.example.game.model.GameSession;
import com.example.game.repository.GameSessionRepository;
import com.example.game.service.interfaces.IGameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService implements IGameSessionService {

    private final GameSessionRepository gameSessionRepository;

    @Autowired
    public GameSessionService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Override
    public GameSession saveGameSession(GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }
}
