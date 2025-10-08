package com.example.game.repository;

import com.example.game.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository r;

    @Test
    void p1() {
        User u = new User();
        u.setUsername("A");
        u.setEmail("a@a");
        u.setPassword("p");
        r.save(u);
        Optional<User> x = r.findByUsername("A");
        assertTrue(x.isPresent());
        assertEquals("A", x.get().getUsername());
    }

    @Test
    void p2() {
        User u = new User();
        u.setUsername("B");
        u.setEmail("b@b");
        u.setPassword("p");
        r.save(u);
        Optional<User> x = r.findByEmail("b@b");
        assertTrue(x.isPresent());
        assertEquals("b@b", x.get().getEmail());
    }

    @Test
    void n1() {
        Optional<User> x = r.findByUsername("Z");
        assertTrue(x.isEmpty());
    }

    @Test
    void p3() {
        User u = new User();
        u.setUsername("C");
        u.setEmail("c@c");
        u.setPassword("p");
        r.save(u);
        assertTrue(r.existsByUsername("C"));
        assertTrue(r.existsByEmail("c@c"));
    }

    @Test
    void n2() {
        assertFalse(r.existsByUsername("X"));
        assertFalse(r.existsByEmail("x@x"));
    }
}
