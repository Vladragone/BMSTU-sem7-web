package com.example.game.service;

import com.example.game.model.GameSession;
import com.example.game.repository.GameSessionRepository;
import com.example.game.service.interfaces.IGameSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GameSessionService implements IGameSessionService {

    private final GameSessionRepository gameSessionRepository;

    public GameSessionService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Override
    public GameSession saveGameSession(GameSession gameSession) {
        try {
            return gameSessionRepository.save(gameSession);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving game session", e);
        }
    }
}
