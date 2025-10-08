package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.repository.LocationRepository;
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

class LocationServiceTest {

    @Mock
    private LocationRepository r;

    @InjectMocks
    private LocationService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        when(r.findDistinctNames()).thenReturn(List.of("A", "B"));
        List<String> x = s.getDistinctLocationNames();
        assertEquals(2, x.size());
    }

    @Test
    void n1() {
        when(r.findDistinctNames()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getDistinctLocationNames());
    }

    @Test
    void p2() {
        Location l = new Location();
        when(r.findAll()).thenReturn(List.of(l));
        List<Location> all = s.getAllLocations();
        assertEquals(1, all.size());
    }

    @Test
    void n2() {
        when(r.findAll()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getAllLocations());
    }

    @Test
    void p3() {
        Location l = new Location();
        l.setName("N");
        when(r.save(l)).thenReturn(l);
        Location x = s.addLocation(l);
        assertEquals("N", x.getName());
    }

    @Test
    void n3() {
        when(r.save(any())).thenThrow(new RuntimeException());
        Location l = new Location();
        assertThrows(ResponseStatusException.class, () -> s.addLocation(l));
    }

    @Test
    void p4() {
        Location l1 = new Location();
        l1.setName("A");
        Location l2 = new Location();
        l2.setName("A");
        when(r.findByName("A")).thenReturn(List.of(l1, l2));
        Location x = s.getRandomLocationByName("A");
        assertNotNull(x);
        assertEquals("A", x.getName());
    }

    @Test
    void n4() {
        when(r.findByName("Z")).thenReturn(List.of());
        Location x = s.getRandomLocationByName("Z");
        assertNull(x);
    }

    @Test
    void n5() {
        when(r.findByName("E")).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getRandomLocationByName("E"));
    }

    @Test
    void p5() {
        when(r.existsById(1L)).thenReturn(true);
        doNothing().when(r).deleteById(1L);
        assertDoesNotThrow(() -> s.deleteLocation(1L));
    }

    @Test
    void n6() {
        when(r.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> s.deleteLocation(1L));
    }

    @Test
    void n7() {
        when(r.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException()).when(r).deleteById(1L);
        assertThrows(ResponseStatusException.class, () -> s.deleteLocation(1L));
    }
}
