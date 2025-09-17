package com.example.game.repository;

import com.example.game.model.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testSaveLocation() {
        Location loc = new Location();
        loc.setName("Moscow");
        loc.setLat(45.0);
        loc.setLng(90.0);

        Location saved = locationRepository.save(loc);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Moscow");
        assertThat(saved.getLat()).isEqualTo(45.0);
        assertThat(saved.getLng()).isEqualTo(90.0);
    }

    @Test
    void testFindByName() {
        Location loc1 = new Location();
        loc1.setName("1");
        loc1.setLat(10.0);
        loc1.setLng(20.0);

        Location loc2 = new Location();
        loc2.setName("2");
        loc2.setLat(15.0);
        loc2.setLng(25.0);

        locationRepository.save(loc1);
        locationRepository.save(loc2);

        List<Location> found = locationRepository.findByName("1");
        assertThat(found).hasSizeGreaterThanOrEqualTo(1);
        assertThat(found).allMatch(l -> "1".equals(l.getName()));
    }

    @Test
    void testFindDistinctNames() {
        Location loc1 = new Location();
        loc1.setName("1");
        loc1.setLat(30.0);
        loc1.setLng(40.0);

        Location loc2 = new Location();
        loc2.setName("2");
        loc2.setLat(35.0);
        loc2.setLng(45.0);

        Location loc3 = new Location();
        loc3.setName("1");
        loc3.setLat(31.0);
        loc3.setLng(41.0);

        locationRepository.save(loc1);
        locationRepository.save(loc2);
        locationRepository.save(loc3);

        List<String> distinctNames = locationRepository.findDistinctNames();
        assertThat(distinctNames).contains("1", "2");
    }
}
