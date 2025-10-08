package com.example.game.repository;

import com.example.game.model.GameSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameSessionRepositoryTest {

    @Autowired
    private GameSessionRepository r;

    private GameSession s(Long u, int e, Long l) {
        GameSession s = new GameSession();
        s.setUserId(u);
        s.setEarnedScore(e);
        s.setLocationId(l);
        return s;
    }

    @Test
    void p1() {
        GameSession s1 = s(1L, 10, 2L);
        s1 = r.save(s1);
        Optional<GameSession> x = r.findById(s1.getId());
        assertTrue(x.isPresent());
        assertEquals(10, x.get().getEarnedScore());
        assertEquals(1L, x.get().getUserId());
        assertEquals(2L, x.get().getLocationId());
    }

    @Test
    void n1() {
        Optional<GameSession> x = r.findById(999L);
        assertTrue(x.isEmpty());
    }

    @Test
    void p2() {
        GameSession s1 = s(1L, 5, 3L);
        GameSession s2 = s(2L, 7, 4L);
        r.saveAll(List.of(s1, s2));
        List<GameSession> all = r.findAll();
        assertEquals(2, all.size());
    }
}
