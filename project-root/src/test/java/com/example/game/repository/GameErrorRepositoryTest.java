package com.example.game.repository;

import com.example.game.model.GameError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameErrorRepositoryTest {

    @Autowired
    private GameErrorRepository r;

    @Test
    void p1() {
        GameError e = new GameError();
        e.setName("N");
        e = r.save(e);
        Optional<GameError> x = r.findById(e.getId());
        assertTrue(x.isPresent());
        assertEquals("N", x.get().getName());
    }

    @Test
    void n1() {
        Optional<GameError> x = r.findById(999L);
        assertTrue(x.isEmpty());
    }

    @Test
    void p2() {
        GameError e1 = new GameError("A");
        GameError e2 = new GameError("B");
        r.saveAll(List.of(e1, e2));
        List<GameError> all = r.findAll();
        assertEquals(2, all.size());
    }
}
