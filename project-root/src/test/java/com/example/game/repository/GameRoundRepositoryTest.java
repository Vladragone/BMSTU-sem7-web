package com.example.game.repository;

import com.example.game.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameRoundRepositoryTest {

    @Autowired
    private GameRoundRepository r;

    @Autowired
    private GameSessionRepository s;

    @Autowired
    private LocationRepository l;

    @Autowired
    private LocationGroupRepository g;

    @Test
    void p1() {
        LocationGroup group = new LocationGroup();
        group.setName("Москва");
        g.save(group);

        Location loc = new Location();
        loc.setLat(55.75);
        loc.setLng(37.61);
        loc.setGroup(group);
        l.save(loc);

        GameSession session = new GameSession();
        session.setUserId(1L);
        session.setTotalScore(0);
        session.setTotalRounds(5);
        session.setLocationGroup(group);
        s.save(session);

        GameRound r1 = new GameRound();
        r1.setSession(session);
        r1.setLocation(loc);
        r1.setScore(100);
        r1.setRoundNumber(1);

        GameRound r2 = new GameRound();
        r2.setSession(session);
        r2.setLocation(loc);
        r2.setScore(80);
        r2.setRoundNumber(2);

        r.saveAll(List.of(r1, r2));

        List<GameRound> found = r.findBySession(session);
        assertEquals(2, found.size());
    }

    @Test
    void p2() {
        LocationGroup group = new LocationGroup();
        group.setName("Париж");
        g.save(group);

        Location loc = new Location();
        loc.setLat(48.85);
        loc.setLng(2.35);
        loc.setGroup(group);
        l.save(loc);

        GameSession session = new GameSession();
        session.setUserId(1L);
        session.setTotalScore(0);
        session.setTotalRounds(3);
        session.setLocationGroup(group);
        s.save(session);

        for (int i = 1; i <= 3; i++) {
            GameRound gr = new GameRound();
            gr.setSession(session);
            gr.setLocation(loc);
            gr.setRoundNumber(i);
            gr.setScore(i * 10);
            r.save(gr);
        }

        GameRound last = r.findTopBySessionOrderByRoundNumberDesc(session);
        assertEquals(3, last.getRoundNumber());
        assertEquals(30, last.getScore());
    }

    @Test
    void n1() {
        GameSession fakeSession = new GameSession();
        fakeSession.setId(999L);
        List<GameRound> notFound = r.findBySession(fakeSession);
        assertTrue(notFound.isEmpty());
    }
}
