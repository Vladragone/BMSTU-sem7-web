package com.example.game.service;

import com.example.game.model.GameSession;
import com.example.game.repository.GameSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSessionServiceTest {

    @Mock
    private GameSessionRepository r;

    @InjectMocks
    private GameSessionService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private GameSession g(Long userId, Long locationId, int score) {
        GameSession g = new GameSession();
        g.setUserId(userId);
        g.setLocationId(locationId);
        g.setEarnedScore(score);
        return g;
    }

    @Test
    void p1() {
        GameSession g = g(1L, 2L, 150);
        when(r.save(any(GameSession.class))).thenReturn(g);
        GameSession x = s.saveGameSession(g);
        assertEquals(150, x.getEarnedScore());
        assertEquals(1L, x.getUserId());
        assertEquals(2L, x.getLocationId());
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        GameSession g = g(3L, 4L, 50);
        assertThrows(ResponseStatusException.class, () -> s.saveGameSession(g));
    }
}
