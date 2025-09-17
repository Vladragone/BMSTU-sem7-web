package com.example.game.repository;

import com.example.game.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUsernameAndEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("123");
        user.setRole("user");
        user.setUsername("testuser");
        userRepository.save(user);

        Optional<User> byUsername = userRepository.findByUsername("testuser");
        assertThat(byUsername).isPresent();
        assertThat(byUsername.get().getEmail()).isEqualTo("test@test.com");

        Optional<User> byEmail = userRepository.findByEmail("test@test.com");
        assertThat(byEmail).isPresent();
        assertThat(byEmail.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testExistsByUsernameAndEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("pass");
        user.setRole("user");
        user.setUsername("testuser");
        userRepository.save(user);

        assertThat(userRepository.existsByUsername("testuser")).isTrue();
        assertThat(userRepository.existsByEmail("test@test.com")).isTrue();

        assertThat(userRepository.existsByUsername("aboba")).isFalse();
        assertThat(userRepository.existsByEmail("aboba@test.com")).isFalse();
    }
}
