package com.example.game.service;

import com.example.game.dto.LocationRequestDTO;
import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
import com.example.game.repository.LocationGroupRepository;
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

    @Mock
    private LocationGroupRepository gRepo;

    @InjectMocks
    private LocationService s;

    private final LocationGroup g = new LocationGroup();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        g.setId(1L);
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
        when(gRepo.findById(1L)).thenReturn(Optional.of(g));
        when(r.findByGroup(g)).thenReturn(List.of(new Location()));

        List<Location> result = s.getLocationsByGroupId(1L);
        assertEquals(1, result.size());
    }

    @Test
    void n2() {
        when(gRepo.findById(1L)).thenReturn(Optional.of(g));
        when(r.findByGroup(g)).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.getLocationsByGroupId(1L));
    }

    @Test
    void p3() {
        LocationRequestDTO dto = new LocationRequestDTO();
        dto.setGroupId(1L);
        dto.setLat(10.0);
        dto.setLng(20.0);

        when(gRepo.findById(1L)).thenReturn(Optional.of(g));

        Location l = new Location();
        l.setGroup(g);
        l.setLat(10.0);
        l.setLng(20.0);

        when(r.save(any(Location.class))).thenReturn(l);

        Location saved = s.addLocationFromDto(dto);

        assertEquals(g, saved.getGroup());
        assertEquals(10.0, saved.getLat());
    }

    @Test
    void n3() {
        LocationRequestDTO dto = new LocationRequestDTO();
        dto.setGroupId(1L);
        dto.setLat(1.0);
        dto.setLng(2.0);

        when(gRepo.findById(1L)).thenReturn(Optional.of(g));
        when(r.save(any())).thenThrow(new RuntimeException());

        assertThrows(ResponseStatusException.class, () -> s.addLocationFromDto(dto));
    }

    @Test
    void p4() {
        Location l1 = new Location();
        l1.setGroup(g);
        Location l2 = new Location();
        l2.setGroup(g);

        when(gRepo.findById(1L)).thenReturn(Optional.of(g));
        when(r.findByGroup(g)).thenReturn(List.of(l1, l2));

        Location random = s.getRandomLocationByGroupId(1L);

        assertNotNull(random);
        assertEquals(g, random.getGroup());
    }

    @Test
    void n4() {
        when(gRepo.findById(1L)).thenReturn(Optional.of(g));
        when(r.findByGroup(g)).thenReturn(List.of());

        assertNull(s.getRandomLocationByGroupId(1L));
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
