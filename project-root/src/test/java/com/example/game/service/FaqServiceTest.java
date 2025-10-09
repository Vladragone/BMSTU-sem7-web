package com.example.game.service.impl;

import com.example.game.dto.FaqUpdateDTO;
import com.example.game.model.Faq;
import com.example.game.repository.FaqRepository;
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

class FaqServiceTest {

    @Mock
    private FaqRepository r;

    @InjectMocks
    private FaqService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        Faq f = new Faq();
        f.setId(1L);
        f.setQuestion("Q");
        f.setAnswer("A");
        when(r.save(f)).thenReturn(f);
        Faq x = s.saveFaq(f);
        assertEquals("Q", x.getQuestion());
        assertEquals("A", x.getAnswer());
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        Faq f = new Faq();
        assertThrows(ResponseStatusException.class, () -> s.saveFaq(f));
    }

    @Test
    void p2() {
        Faq f = new Faq();
        f.setId(1L);
        f.setQuestion("Q");
        f.setAnswer("A");
        when(r.findById(1L)).thenReturn(Optional.of(f));
        FaqUpdateDTO u = new FaqUpdateDTO();
        u.setAnswer("B");
        when(r.save(any())).thenReturn(f);
        Faq x = s.updateFaq(1L, u);
        assertEquals("B", x.getAnswer());
    }

    @Test
    void n2() {
        when(r.findById(1L)).thenReturn(Optional.empty());
        FaqUpdateDTO u = new FaqUpdateDTO();
        assertThrows(ResponseStatusException.class, () -> s.updateFaq(1L, u));
    }

    @Test
    void p3() {
        Faq f = new Faq();
        f.setId(1L);
        when(r.existsById(1L)).thenReturn(true);
        doNothing().when(r).deleteById(1L);
        assertDoesNotThrow(() -> s.deleteFaq(1L));
    }

    @Test
    void n3() {
        when(r.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> s.deleteFaq(1L));
    }

    @Test
    void p4() {
        Faq f = new Faq();
        when(r.findAll()).thenReturn(List.of(f));
        List<Faq> all = s.getAllFaqs();
        assertEquals(1, all.size());
    }

    @Test
    void p5() {
        Faq f = new Faq();
        when(r.findById(1L)).thenReturn(Optional.of(f));
        Optional<Faq> x = s.getFaqById(1L);
        assertTrue(x.isPresent());
    }
}
