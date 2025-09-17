package com.example.game.repository;

import com.example.game.model.Profile;
import com.example.game.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        profileRepository.deleteAll();
        userRepository.deleteAll();
    }

    private User createAndSaveUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@test.com");
        user.setPassword("123");
        user.setRole("user");
        return userRepository.save(user);
    }

    @Test
    void testFindByUserUsername() {
        User user = createAndSaveUser("vlad");
        Profile profile = new Profile(user, LocalDateTime.now());
        profile.setScore(123);
        profileRepository.save(profile);

        Optional<Profile> found = profileRepository.findByUserUsername("vlad");
        assertThat(found).isPresent();
        assertThat(found.get().getScore()).isEqualTo(123);
        assertThat(found.get().getUser().getUsername()).isEqualTo("vlad");
    }

    @Test
    void testFindByUserUsernameNotFound() {
        Optional<Profile> found = profileRepository.findByUserUsername("aboba");
        assertThat(found).isNotPresent();
    }

    @Test
    void testFindTop10ByOrderByScoreDesc() {
        for (int i = 1; i <= 12; i++) {
            User user = createAndSaveUser("user" + i);
            Profile profile = new Profile(user, LocalDateTime.now());
            profile.setScore(i * 10);
            profileRepository.save(profile);
        }

        List<Profile> topProfiles = profileRepository.findTop10ByOrderByScoreDesc();

        assertThat(topProfiles).hasSize(10);
        int previousScore = Integer.MAX_VALUE;
        for (Profile profile : topProfiles) {
            assertThat(profile.getScore()).isLessThanOrEqualTo(previousScore);
            previousScore = profile.getScore();
        }

        assertThat(topProfiles.get(0).getScore()).isEqualTo(120);
        assertThat(topProfiles.get(9).getScore()).isEqualTo(30);
    }
}
