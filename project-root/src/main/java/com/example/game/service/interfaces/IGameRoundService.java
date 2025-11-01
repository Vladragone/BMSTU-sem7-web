package com.example.game.service.interfaces;

import com.example.game.dto.GameRoundRequestDTO;
import com.example.game.model.GameRound;

import java.util.List;
import java.util.Optional;

public interface IGameRoundService {

    List<GameRound> getAllRounds();
    
    List<GameRound> getRoundsBySessionId(Long sessionId);

    GameRound createFromDto(GameRoundRequestDTO dto);

    GameRound getCurrentRoundBySessionId(Long sessionId);

    GameRound saveRound(GameRound round);
    
    Optional<GameRound> getRoundById(Long id);
}