package com.example.game.service;

import com.example.game.model.GameSession;
import com.example.game.model.LocationGroup;
import com.example.game.repository.GameSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    private GameSession g(Long userId, int score, int rounds) {
        LocationGroup group = new LocationGroup();
        group.setName("Москва");

        GameSession gs = new GameSession();
        gs.setUserId(userId);
        gs.setTotalScore(score);
        gs.setTotalRounds(rounds);
        gs.setLocationGroup(group);
        return gs;
    }

    @Test
    void p1() {
        GameSession x = g(1L, 200, 5);
        when(r.save(any())).thenReturn(x);

        GameSession saved = s.saveGameSession(x);

        assertEquals(1L, saved.getUserId());
        assertEquals(200, saved.getTotalScore());
        assertEquals(5, saved.getTotalRounds());
        assertEquals("Москва", saved.getLocationGroup().getName());
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        GameSession x = g(1L, 50, 3);

        assertThrows(ResponseStatusException.class, () -> s.saveGameSession(x));
    }

    @Test
    void p2() {
        when(r.findByUserId(1L)).thenReturn(List.of(g(1L, 100, 3)));
        List<GameSession> list = s.getSessionsByUser(1L);

        assertEquals(1, list.size());
        assertEquals(100, list.get(0).getTotalScore());
    }

    @Test
    void p3() {
        GameSession x = g(1L, 150, 4);
        when(r.findById(1L)).thenReturn(Optional.of(x));

        Optional<GameSession> found = s.getSessionById(1L);
        assertTrue(found.isPresent());
        assertEquals(150, found.get().getTotalScore());
    }

    @Test
    void p4() {
        when(r.existsById(1L)).thenReturn(true);
        doNothing().when(r).deleteById(1L);

        assertDoesNotThrow(() -> s.deleteSession(1L));
    }

    @Test
    void n2() {
        when(r.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> s.deleteSession(1L));
    }
}
