package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
import com.example.game.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private LocationRepository r;

    @InjectMocks
    private LocationService s;

    private final LocationGroup g = new LocationGroup();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        g.setName("Москва");
    }

    @Test
    void p1() {
        Location l = new Location();
        l.setLat(55.75);
        l.setLng(37.61);
        l.setGroup(g);

        when(r.findAll()).thenReturn(List.of(l));

        List<Location> all = s.getAllLocations();
        assertEquals(1, all.size());
        assertEquals(g, all.get(0).getGroup());
    }

    @Test
    void n1() {
        when(r.findAll()).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, s::getAllLocations);
    }

    @Test
    void p2() {
        when(r.findByGroup(g)).thenReturn(List.of(new Location()));
        List<Location> result = s.getLocationsByGroup(g);
        assertEquals(1, result.size());
    }

    @Test
    void n2() {
        when(r.findByGroup(g)).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getLocationsByGroup(g));
    }

    @Test
    void p3() {
        Location l = new Location();
        l.setGroup(g);
        l.setLat(10.0);
        l.setLng(20.0);

        when(r.save(l)).thenReturn(l);
        Location saved = s.addLocation(l);

        assertEquals(g, saved.getGroup());
        assertEquals(10.0, saved.getLat());
    }

    @Test
    void n3() {
        when(r.save(any())).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.addLocation(new Location()));
    }

    @Test
    void p4() {
        Location l1 = new Location();
        l1.setGroup(g);
        Location l2 = new Location();
        l2.setGroup(g);

        when(r.findByGroup(g)).thenReturn(List.of(l1, l2));
        Location random = s.getRandomLocationByGroup(g);

        assertNotNull(random);
        assertEquals(g, random.getGroup());
    }

    @Test
    void n4() {
        when(r.findByGroup(g)).thenReturn(List.of());
        assertNull(s.getRandomLocationByGroup(g));
    }

    @Test
    void p5() {
        when(r.existsById(1L)).thenReturn(true);
        doNothing().when(r).deleteById(1L);
        assertDoesNotThrow(() -> s.deleteLocation(1L));
    }

    @Test
    void n5() {
        when(r.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> s.deleteLocation(1L));
    }

    @Test
    void n6() {
        when(r.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException()).when(r).deleteById(1L);
        assertThrows(ResponseStatusException.class, () -> s.deleteLocation(1L));
    }
}
