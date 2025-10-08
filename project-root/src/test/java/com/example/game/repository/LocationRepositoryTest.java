package com.example.game.repository;

import com.example.game.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository r;

    @Test
    void p1() {
        Location l1 = new Location();
        l1.setName("A");
        Location l2 = new Location();
        l2.setName("B");
        r.saveAll(List.of(l1, l2));
        List<String> n = r.findDistinctNames();
        assertTrue(n.contains("A"));
        assertTrue(n.contains("B"));
    }

    @Test
    void p2() {
        Location l = new Location();
        l.setName("X");
        r.save(l);
        List<Location> x = r.findByName("X");
        assertEquals(1, x.size());
        assertEquals("X", x.get(0).getName());
    }

    @Test
    void n1() {
        List<Location> x = r.findByName("Z");
        assertTrue(x.isEmpty());
    }
}
