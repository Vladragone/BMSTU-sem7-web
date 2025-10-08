package com.example.game.service;

import com.example.game.dto.ProfileUpdateRequest;
import com.example.game.model.Profile;
import com.example.game.model.User;
import com.example.game.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository r;

    @InjectMocks
    private ProfileService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        Profile p = new Profile();
        User u = new User();
        u.setUsername("A");
        p.setUser(u);
        when(r.findByUserUsername("A")).thenReturn(Optional.of(p));
        Profile x = s.getProfile("A");
        assertNotNull(x);
        assertEquals("A", x.getUser().getUsername());
    }

    @Test
    void n1() {
        when(r.findByUserUsername("Z")).thenReturn(Optional.empty());
        Profile x = s.getProfile("Z");
        assertNull(x);
    }

    @Test
    void n2() {
        when(r.findByUserUsername("A")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getProfile("A"));
    }

    @Test
    void p2() {
        Profile p = new Profile();
        p.setScore(5);
        p.setGameNum(1);
        User u = new User();
        u.setUsername("A");
        p.setUser(u);
        when(r.findByUserUsername("A")).thenReturn(Optional.of(p));
        when(r.save(any())).thenReturn(p);
        ProfileUpdateRequest uReq = new ProfileUpdateRequest();
        uReq.setScore(10);
        Profile x = s.updateProfile(uReq, "A");
        assertEquals(15, x.getScore());
        assertEquals(2, x.getGameNum());
    }

    @Test
    void n3() {
        when(r.findByUserUsername("A")).thenReturn(Optional.empty());
        ProfileUpdateRequest uReq = new ProfileUpdateRequest();
        uReq.setScore(10);
        Profile x = s.updateProfile(uReq, "A");
        assertNull(x);
    }

    @Test
    void n4() {
        when(r.findByUserUsername("A")).thenThrow(new RuntimeException());
        ProfileUpdateRequest uReq = new ProfileUpdateRequest();
        assertThrows(ResponseStatusException.class, () -> s.updateProfile(uReq, "A"));
    }
}
