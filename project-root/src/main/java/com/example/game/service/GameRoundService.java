package com.example.game.service;

import com.example.game.model.GameRound;
import com.example.game.model.GameSession;
import com.example.game.repository.GameRoundRepository;
import com.example.game.service.interfaces.IGameRoundService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GameRoundService implements IGameRoundService {

    private final GameRoundRepository gameRoundRepository;

    public GameRoundService(GameRoundRepository gameRoundRepository) {
        this.gameRoundRepository = gameRoundRepository;
    }

    @Override
    public List<GameRound> getRoundsBySession(GameSession session) {
        return gameRoundRepository.findBySession(session);
    }

    @Override
    public GameRound getCurrentRound(GameSession session) {
        return gameRoundRepository.findTopBySessionOrderByRoundNumberDesc(session);
    }

    @Override
    public GameRound saveRound(GameRound round) {
        try {
            return gameRoundRepository.save(round);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при сохранении раунда", e);
        }
    }
}
