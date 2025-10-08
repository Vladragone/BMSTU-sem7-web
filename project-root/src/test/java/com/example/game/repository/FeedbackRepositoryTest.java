package com.example.game.repository;

import com.example.game.model.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository r;

    private Feedback f(Long u, int r1, String p, String d) {
        Feedback f = new Feedback();
        f.setUserId(u);
        f.setRating(r1);
        f.setProblem(p);
        f.setDescription(d);
        return f;
    }

    @Test
    void p1() {
        Feedback f1 = f(1L, 5, "P", "D");
        Feedback s = r.save(f1);
        Optional<Feedback> x = r.findById(s.getId());
        assertTrue(x.isPresent());
        assertEquals("P", x.get().getProblem());
        assertEquals("D", x.get().getDescription());
    }

    @Test
    void n1() {
        Optional<Feedback> x = r.findById(999L);
        assertTrue(x.isEmpty());
    }

    @Test
    void p2() {
        Feedback f1 = f(1L, 4, "A", "B");
        Feedback f2 = f(2L, 3, "C", "D");
        r.saveAll(List.of(f1, f2));
        List<Feedback> all = r.findAll();
        assertEquals(2, all.size());
    }
}
