package com.example.game.service.interfaces;

import com.example.game.dto.GameSessionRequestDTO;
import com.example.game.model.GameSession;

import java.util.List;
import java.util.Optional;

public interface IGameSessionService {

    List<GameSession> getAllSessions();
    
    List<GameSession> getSessionsByUser(Long userId);

    GameSession createFromDto(GameSessionRequestDTO dto);

    GameSession saveGameSession(GameSession gameSession);

    Optional<GameSession> getSessionById(Long id);

    void deleteSession(Long id);
}