package com.example.game.repository;

import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository r;

    @Autowired
    private LocationGroupRepository g;

    @Test
    void p1() {
        LocationGroup g1 = new LocationGroup();
        g1.setName("Москва");
        LocationGroup g2 = new LocationGroup();
        g2.setName("Париж");
        g.saveAll(List.of(g1, g2));

        Location l1 = new Location();
        l1.setLat(55.751244);
        l1.setLng(37.618423);
        l1.setGroup(g1);

        Location l2 = new Location();
        l2.setLat(48.8566);
        l2.setLng(2.3522);
        l2.setGroup(g2);

        r.saveAll(List.of(l1, l2));

        List<Location> moscow = r.findByGroup(g1);
        List<Location> paris = r.findByGroup(g2);

        assertEquals(1, moscow.size());
        assertEquals(1, paris.size());
        assertEquals(g1.getId(), moscow.get(0).getGroup().getId());
        assertEquals(g2.getId(), paris.get(0).getGroup().getId());
    }

    @Test
    void n1() {
        LocationGroup empty = new LocationGroup();
        empty.setName("Пустая");
        g.save(empty);

        List<Location> x = r.findByGroup(empty);
        assertTrue(x.isEmpty());
    }
}
