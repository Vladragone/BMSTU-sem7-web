package com.example.game.service;

import com.example.game.model.LocationGroup;
import com.example.game.repository.LocationGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationGroupServiceTest {

    @Mock
    private LocationGroupRepository r;

    @InjectMocks
    private LocationGroupService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        LocationGroup g = new LocationGroup();
        g.setName("Москва");
        when(r.save(g)).thenReturn(g);

        LocationGroup saved = s.addGroup(g);
        assertEquals("Москва", saved.getName());
    }

    @Test
    void n1() {
        when(r.save(any())).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.addGroup(new LocationGroup()));
    }

    @Test
    void p2() {
        LocationGroup g1 = new LocationGroup();
        g1.setName("Европа");
        LocationGroup g2 = new LocationGroup();
        g2.setName("Азия");

        when(r.findAll()).thenReturn(List.of(g1, g2));
        List<LocationGroup> list = s.getAllGroups();

        assertEquals(2, list.size());
        assertEquals("Европа", list.get(0).getName());
    }

    @Test
    void n2() {
        when(r.findAll()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, s::getAllGroups);
    }

    @Test
    void p3() {
        LocationGroup g = new LocationGroup();
        g.setName("Африка");
        when(r.findByName("Африка")).thenReturn(g);

        LocationGroup found = s.getGroupByName("Африка");
        assertEquals("Африка", found.getName());
    }

    @Test
    void n3() {
        when(r.findByName("Америка")).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> s.getGroupByName("Америка"));
    }

    @Test
    void p4() {
        when(r.existsById(1L)).thenReturn(true);
        doNothing().when(r).deleteById(1L);
        assertDoesNotThrow(() -> s.deleteGroup(1L));
    }

    @Test
    void n4() {
        when(r.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> s.deleteGroup(1L));
    }

    @Test
    void n5() {
        when(r.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException()).when(r).deleteById(1L);
        assertThrows(ResponseStatusException.class, () -> s.deleteGroup(1L));
    }
}
