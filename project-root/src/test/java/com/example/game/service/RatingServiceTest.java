package com.example.game.service;

import com.example.game.dto.RatingResponse;
import com.example.game.model.Profile;
import com.example.game.model.User;
import com.example.game.repository.ProfileRepository;
import com.example.game.service.interfaces.ITokenParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private ProfileRepository r;

    @Mock
    private ITokenParser t;

    @InjectMocks
    private RatingService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private Profile p(String u, int score, int games) {
        Profile p = new Profile();
        User user = new User();
        user.setUsername(u);
        p.setUser(user);
        p.setScore(score);
        p.setGameNum(games);
        return p;
    }

    @Test
    void p1() {
        when(t.getUsername("T")).thenReturn("U1");
        List<Profile> list = List.of(p("U1", 10, 2), p("U2", 5, 3));
        when(r.findAll()).thenReturn(list);
        RatingResponse res = s.getSortedRatingAndRank("T", "score", 2);
        assertNotNull(res);
        assertEquals(2, res.getTop().size());
        assertEquals(1, res.getYourRank());
        assertEquals("score", res.getSortBy());
    }

    @Test
    void p2() {
        when(t.getUsername("T")).thenReturn("U1");
        List<Profile> list = List.of(p("U1", 10, 2), p("U2", 5, 3));
        when(r.findAll()).thenReturn(list);
        RatingResponse res = s.getSortedRatingAndRank("T", "games", 2);
        assertNotNull(res);
        assertEquals(2, res.getTop().size());
        assertEquals("games", res.getSortBy());
    }

    @Test
    void n1() {
        when(t.getUsername("T")).thenReturn("U1");
        when(r.findAll()).thenReturn(List.of());
        assertThrows(ResponseStatusException.class, () -> s.getSortedRatingAndRank("T", "score", 2));
    }

    @Test
    void n2() {
        when(t.getUsername("T")).thenReturn("U3");
        List<Profile> list = List.of(p("U1", 10, 2), p("U2", 5, 3));
        when(r.findAll()).thenReturn(list);
        assertThrows(ResponseStatusException.class, () -> s.getSortedRatingAndRank("T", "score", 2));
    }

    @Test
    void n3() {
        when(t.getUsername("T")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getSortedRatingAndRank("T", "score", 2));
    }
}
