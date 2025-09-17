package com.example.game.service;

import com.example.game.model.GameError;
import com.example.game.repository.GameErrorRepository;
import com.example.game.service.interfaces.IGameErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameErrorService implements IGameErrorService {

    private final GameErrorRepository gameErrorRepository;

    @Autowired
    public GameErrorService(GameErrorRepository gameErrorRepository) {
        this.gameErrorRepository = gameErrorRepository;
    }

    @Override
    public List<GameError> getAllGameErrors() {
        return gameErrorRepository.findAll();
    }
}
