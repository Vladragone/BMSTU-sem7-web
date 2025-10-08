package com.example.game.service;

import com.example.game.model.GameSession;
import com.example.game.repository.GameSessionRepository;
import com.example.game.service.interfaces.IGameSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при сохранении игровой сессии", e);
        }
    }

    @Override
    public List<GameSession> getSessionsByUser(Long userId) {
        try {
            return gameSessionRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении сессий пользователя", e);
        }
    }

    @Override
    public Optional<GameSession> getSessionById(Long id) {
        return gameSessionRepository.findById(id);
    }

    @Override
    public void deleteSession(Long id) {
        if (!gameSessionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сессия не найдена");
        }
        gameSessionRepository.deleteById(id);
    }
}
