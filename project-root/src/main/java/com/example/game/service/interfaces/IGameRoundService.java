package com.example.game.service.interfaces;

import com.example.game.model.GameRound;
import com.example.game.model.GameSession;
import java.util.List;

public interface IGameRoundService {

    List<GameRound> getRoundsBySession(GameSession session);
    GameRound getCurrentRound(GameSession session);
    GameRound saveRound(GameRound round);
}
