package com.example.game.service;

import com.example.game.model.GameRound;
import com.example.game.model.GameSession;
import com.example.game.repository.GameRoundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRoundServiceTest {

    @Mock
    private GameRoundRepository r;

    @InjectMocks
    private GameRoundService s;

    private final GameSession session = new GameSession();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        when(r.findBySession(session)).thenReturn(List.of(new GameRound()));
        List<GameRound> rounds = s.getRoundsBySession(session);
        assertEquals(1, rounds.size());
    }

    @Test
    void p2() {
        GameRound round = new GameRound();
        round.setRoundNumber(5);
        when(r.findTopBySessionOrderByRoundNumberDesc(session)).thenReturn(round);

        GameRound result = s.getCurrentRound(session);
        assertEquals(5, result.getRoundNumber());
    }

    @Test
    void p3() {
        GameRound round = new GameRound();
        when(r.save(round)).thenReturn(round);
        GameRound saved = s.saveRound(round);
        assertNotNull(saved);
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.saveRound(new GameRound()));
    }
}
