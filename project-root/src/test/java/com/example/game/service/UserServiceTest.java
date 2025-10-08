package com.example.game.service;

import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository r;

    @InjectMocks
    private UserService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        when(r.existsByEmail("a@a")).thenReturn(true);
        boolean x = s.existsByEmail("a@a");
        assertTrue(x);
    }

    @Test
    void n1() {
        when(r.existsByEmail("a@a")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.existsByEmail("a@a"));
    }

    @Test
    void p2() {
        when(r.existsByUsername("A")).thenReturn(true);
        boolean x = s.existsByUsername("A");
        assertTrue(x);
    }

    @Test
    void n2() {
        when(r.existsByUsername("A")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.existsByUsername("A"));
    }

    @Test
    void p3() {
        User u = new User();
        u.setUsername("A");
        when(r.findByUsername("A")).thenReturn(Optional.of(u));
        User x = s.findUserByUsername("A");
        assertEquals("A", x.getUsername());
    }

    @Test
    void n3() {
        when(r.findByUsername("Z")).thenReturn(Optional.empty());
        User x = s.findUserByUsername("Z");
        assertNull(x);
    }

    @Test
    void n4() {
        when(r.findByUsername("A")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.findUserByUsername("A"));
    }
}
