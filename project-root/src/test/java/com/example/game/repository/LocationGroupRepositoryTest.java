package com.example.game.repository;

import com.example.game.model.LocationGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationGroupRepositoryTest {

    @Autowired
    private LocationGroupRepository r;

    @Test
    void p1() {
        LocationGroup g1 = new LocationGroup();
        g1.setName("Европа");
        r.save(g1);

        LocationGroup found = r.findByName("Европа");
        assertNotNull(found);
        assertEquals("Европа", found.getName());
    }

    @Test
    void p2() {
        LocationGroup g1 = new LocationGroup();
        g1.setName("Москва");
        LocationGroup g2 = new LocationGroup();
        g2.setName("Азия");
        r.saveAll(List.of(g1, g2));

        List<LocationGroup> all = r.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void n1() {
        LocationGroup x = r.findByName("Неизвестная");
        assertNull(x);
    }
}
