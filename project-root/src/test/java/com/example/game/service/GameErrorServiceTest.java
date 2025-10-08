package com.example.game.service;

import com.example.game.model.GameError;
import com.example.game.repository.GameErrorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameErrorServiceTest {

    @Mock
    private GameErrorRepository r;

    @InjectMocks
    private GameErrorService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private GameError e(String name) {
        GameError e = new GameError();
        e.setName(name);
        return e;
    }

    @Test
    void p1() {
        GameError err = e("NullPointer");
        when(r.findAll()).thenReturn(List.of(err));
        List<GameError> all = s.getAllGameErrors();
        assertEquals(1, all.size());
        assertEquals("NullPointer", all.get(0).getName());
    }

    @Test
    void n1() {
        when(r.findAll()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getAllGameErrors());
    }
}
