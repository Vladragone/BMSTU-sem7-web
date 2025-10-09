package com.example.game.service;

import com.example.game.dto.GameRoundRequestDTO;
import com.example.game.model.GameRound;
import com.example.game.model.GameSession;
import com.example.game.model.Location;
import com.example.game.repository.GameRoundRepository;
import com.example.game.repository.GameSessionRepository;
import com.example.game.repository.LocationRepository;
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

class GameRoundServiceTest {

    @Mock
    private GameRoundRepository r;

    @Mock
    private GameSessionRepository sessionRepo;

    @Mock
    private LocationRepository locationRepo;

    @InjectMocks
    private GameRoundService s;

    private final GameSession session = new GameSession();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        session.setId(1L);
    }

    @Test
    void p1() {
        when(sessionRepo.findById(1L)).thenReturn(Optional.of(session));
        when(r.findBySession(session)).thenReturn(List.of(new GameRound()));

        List<GameRound> rounds = s.getRoundsBySessionId(1L);

        assertEquals(1, rounds.size());
    }

    @Test
    void p2() {
        GameRound round = new GameRound();
        round.setRoundNumber(5);

        when(sessionRepo.findById(1L)).thenReturn(Optional.of(session));
        when(r.findTopBySessionOrderByRoundNumberDesc(session)).thenReturn(round);

        GameRound result = s.getCurrentRoundBySessionId(1L);

        assertEquals(5, result.getRoundNumber());
    }

    @Test
    void p3() {
        GameRoundRequestDTO dto = new GameRoundRequestDTO();
        dto.setSessionId(1L);
        dto.setLocationId(2L);
        dto.setGuessLat(55.75);
        dto.setGuessLng(37.61);
        dto.setScore(100);
        dto.setRoundNumber(1);

        Location location = new Location();
        location.setId(2L);

        when(sessionRepo.findById(1L)).thenReturn(Optional.of(session));
        when(locationRepo.findById(2L)).thenReturn(Optional.of(location));

        GameRound savedRound = new GameRound();
        when(r.save(any(GameRound.class))).thenReturn(savedRound);

        GameRound saved = s.createFromDto(dto);

        assertNotNull(saved);
        verify(r, times(1)).save(any(GameRound.class));
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.saveRound(new GameRound()));
    }
}
