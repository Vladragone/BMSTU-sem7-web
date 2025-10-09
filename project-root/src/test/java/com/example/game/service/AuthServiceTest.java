package com.example.game.service;

import com.example.game.dto.LoginRequestDTO;
import com.example.game.dto.TokenResponseDTO;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository r;

    @InjectMocks
    private AuthService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        User u = new User();
        u.setId(1L);
        u.setUsername("A");
        u.setRole("R");
        u.setPassword(hash("p"));
        when(r.findByUsername("A")).thenReturn(Optional.of(u));

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("A");
        req.setPassword("p");

        TokenResponseDTO t = s.authenticateUser(req);

        assertNotNull(t);
        assertTrue(t.getToken().length() > 10);
    }

    @Test
    void n1() {
        when(r.findByUsername("X")).thenReturn(Optional.empty());
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("X");
        req.setPassword("p");
        assertThrows(ResponseStatusException.class, () -> s.authenticateUser(req));
    }

    @Test
    void n2() {
        User u = new User();
        u.setId(1L);
        u.setUsername("A");
        u.setRole("R");
        u.setPassword("wrong");
        when(r.findByUsername("A")).thenReturn(Optional.of(u));

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("A");
        req.setPassword("p");

        assertThrows(ResponseStatusException.class, () -> s.authenticateUser(req));
    }

    private String hash(String p) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(p.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
