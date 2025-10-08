package com.example.game.repository;

import com.example.game.model.GameSession;
import com.example.game.model.LocationGroup;
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

    @Autowired
    private LocationGroupRepository g;

    private GameSession s(Long userId, int score, int rounds, LocationGroup group) {
        GameSession s = new GameSession();
        s.setUserId(userId);
        s.setTotalScore(score);
        s.setTotalRounds(rounds);
        s.setLocationGroup(group);
        return s;
    }

    @Test
    void p1() {
        LocationGroup group = new LocationGroup();
        group.setName("Москва");
        g.save(group);

        GameSession s1 = s(1L, 100, 5, group);
        s1 = r.save(s1);

        Optional<GameSession> x = r.findById(s1.getId());
        assertTrue(x.isPresent());
        assertEquals(100, x.get().getTotalScore());
        assertEquals(1L, x.get().getUserId());
        assertEquals(5, x.get().getTotalRounds());
        assertEquals(group.getId(), x.get().getLocationGroup().getId());
    }

    @Test
    void p2() {
        LocationGroup group = new LocationGroup();
        group.setName("Париж");
        g.save(group);

        GameSession s1 = s(1L, 50, 3, group);
        GameSession s2 = s(2L, 60, 4, group);
        r.saveAll(List.of(s1, s2));

        List<GameSession> user1Sessions = r.findByUserId(1L);
        assertEquals(1, user1Sessions.size());
        assertEquals(1L, user1Sessions.get(0).getUserId());
    }

    @Test
    void n1() {
        Optional<GameSession> x = r.findById(999L);
        assertTrue(x.isEmpty());
    }
}
