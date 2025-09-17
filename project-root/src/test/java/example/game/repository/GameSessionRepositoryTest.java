package com.example.game.repository;

import com.example.game.model.GameSession;
import com.example.game.model.Location;
import com.example.game.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameSessionRepositoryTest {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Location location = new Location();
        location.setName("Moscow");
        location.setLat(10.0);
        location.setLng(20.0);
        locationRepository.save(location);

        GameSession session = new GameSession();
        session.setUserId(user.getId());
        session.setUserLat(10.0);
        session.setUserLng(20.0);
        session.setCorrectLat(location.getLat());
        session.setCorrectLng(location.getLng());
        session.setEarnedScore(100);

        gameSessionRepository.save(session);

        GameSession found = gameSessionRepository.findById(session.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUserId()).isEqualTo(user.getId());
        assertThat(found.getEarnedScore()).isEqualTo(100);
    }

    @Test
    public void testUpdateGameSession() {
        User user = new User();
        user.setUsername("updateuser");
        user.setEmail("updateuser@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Location location = new Location();
        location.setName("Moscow");
        location.setLat(15.0);
        location.setLng(25.0);
        locationRepository.save(location);

        GameSession session = new GameSession();
        session.setUserId(user.getId());
        session.setUserLat(15.0);
        session.setUserLng(25.0);
        session.setCorrectLat(location.getLat());
        session.setCorrectLng(location.getLng());
        session.setEarnedScore(50);

        gameSessionRepository.save(session);

        session.setEarnedScore(75);
        gameSessionRepository.save(session);

        GameSession updated = gameSessionRepository.findById(session.getId()).orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getEarnedScore()).isEqualTo(75);
    }

    @Test
    public void testDeleteGameSession() {
        User user = new User();
        user.setUsername("deleteuser");
        user.setEmail("deleteuser@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Location location = new Location();
        location.setName("Moscow");
        location.setLat(30.0);
        location.setLng(40.0);
        locationRepository.save(location);

        GameSession session = new GameSession();
        session.setUserId(user.getId());
        session.setUserLat(30.0);
        session.setUserLng(40.0);
        session.setCorrectLat(location.getLat());
        session.setCorrectLng(location.getLng());
        session.setEarnedScore(10);

        gameSessionRepository.save(session);
        Long id = session.getId();

        gameSessionRepository.delete(session);

        assertThat(gameSessionRepository.findById(id)).isEmpty();
    }
}
