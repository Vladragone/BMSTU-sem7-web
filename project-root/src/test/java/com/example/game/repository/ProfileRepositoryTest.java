package com.example.game.repository;

import com.example.game.model.Profile;
import com.example.game.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository r;

    @Autowired
    private UserRepository ur;

    @Test
    void p1() {
        User u = new User();
        u.setUsername("U1");
        ur.save(u);

        Profile p = new Profile();
        p.setUser(u);
        p.setScore(5);
        p.setRegDate(LocalDateTime.now());
        r.save(p);

        Optional<Profile> x = r.findByUserUsername("U1");
        assertTrue(x.isPresent());
        assertEquals("U1", x.get().getUser().getUsername());
    }

    @Test
    void n1() {
        Optional<Profile> x = r.findByUserUsername("Z");
        assertTrue(x.isEmpty());
    }

    @Test
    void p2() {
        for (int i = 1; i <= 15; i++) {
            User u = new User();
            u.setUsername("U" + i);
            ur.save(u);

            Profile p = new Profile();
            p.setUser(u);
            p.setScore(i);
            p.setRegDate(LocalDateTime.now());
            r.save(p);
        }

        List<Profile> top = r.findTop10ByOrderByScoreDesc();
        assertEquals(10, top.size());
        assertTrue(top.get(0).getScore() >= top.get(9).getScore());
    }
}
