package com.example.game.repository;

import com.example.game.model.Faq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FaqRepositoryTest {

    @Autowired
    private FaqRepository r;

    @Test
    void p1() {
        Faq f = new Faq();
        f.setQuestion("Q1");
        f.setAnswer("A1");
        Faq saved = r.save(f);
        assertNotNull(saved.getId());
        assertEquals("Q1", saved.getQuestion());
        assertEquals("A1", saved.getAnswer());
    }

    @Test
    void n1() {
        Faq f = new Faq();
        f.setId(999L);
        f.setQuestion(null);
        f.setAnswer(null);
        assertThrows(DataAccessException.class, () -> {
            r.saveAndFlush(f);
        });
    }
}
