package com.example.game.service;

import com.example.game.model.Feedback;
import com.example.game.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository r;

    @InjectMocks
    private FeedbackService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private Feedback f(Long id, Long userId, int rating, String problem, String desc) {
        Feedback f = new Feedback();
        f.setId(id);
        f.setUserId(userId);
        f.setRating(rating);
        f.setProblem(problem);
        f.setDescription(desc);
        return f;
    }

    @Test
    void p1() {
        Feedback f = f(1L, 10L, 5, "Bug", "Crash");
        when(r.save(any(Feedback.class))).thenReturn(f);
        Feedback x = s.saveFeedback(f);
        assertEquals("Bug", x.getProblem());
        assertEquals("Crash", x.getDescription());
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        Feedback f = f(null, 1L, 3, "Issue", "Test");
        assertThrows(ResponseStatusException.class, () -> s.saveFeedback(f));
    }

    @Test
    void p2() {
        Feedback f = f(2L, 12L, 4, "Glitch", "UI freezes");
        when(r.findAll()).thenReturn(List.of(f));
        List<Feedback> all = s.getAllFeedbacks();
        assertEquals(1, all.size());
        assertEquals("Glitch", all.get(0).getProblem());
    }

    @Test
    void n2() {
        when(r.findAll()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getAllFeedbacks());
    }

    @Test
    void p3() {
        Feedback f = f(3L, 15L, 5, "Freeze", "Screen stuck");
        when(r.findById(3L)).thenReturn(Optional.of(f));
        Feedback x = s.getFeedbackById(3L);
        assertEquals("Freeze", x.getProblem());
    }

    @Test
    void n3() {
        when(r.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> s.getFeedbackById(99L));
    }
}
