package com.example.game.service;

import com.example.game.dto.UserRequestDTO;
import com.example.game.dto.UserResponseDTO;
import com.example.game.model.Profile;
import com.example.game.model.User;
import com.example.game.repository.ProfileRepository;
import com.example.game.repository.UserRepository;
import com.example.game.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private UserRepository ur;

    @Mock
    private ProfileRepository pr;

    @Mock
    private IUserService us;

    @InjectMocks
    private RegistrationService s;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void p1() {
        UserRequestDTO r = new UserRequestDTO();
        r.setUsername("A");
        r.setEmail("a@a");
        r.setPassword("p");
        when(us.existsByUsername("A")).thenReturn(false);
        when(us.existsByEmail("a@a")).thenReturn(false);
        User u = new User();
        u.setId(1L);
        u.setUsername("A");
        u.setEmail("a@a");
        u.setRole("user");
        when(ur.save(any())).thenReturn(u);
        when(pr.save(any(Profile.class))).thenReturn(null);
        UserResponseDTO x = s.register(r);
        assertEquals("A", x.getUsername());
        verify(ur, times(1)).save(any());
        verify(pr, times(1)).save(any());
    }

    @Test
    void n1() {
        UserRequestDTO r = new UserRequestDTO();
        r.setUsername("A");
        r.setEmail("a@a");
        when(us.existsByUsername("A")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> s.register(r));
    }

    @Test
    void n2() {
        UserRequestDTO r = new UserRequestDTO();
        r.setUsername("A");
        r.setEmail("a@a");
        when(us.existsByUsername("A")).thenReturn(false);
        when(us.existsByEmail("a@a")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> s.register(r));
    }

    @Test
    void n3() {
        UserRequestDTO r = new UserRequestDTO();
        r.setUsername("A");
        r.setEmail("a@a");
        r.setPassword("p");
        when(us.existsByUsername("A")).thenReturn(false);
        when(us.existsByEmail("a@a")).thenReturn(false);
        when(ur.save(any())).thenThrow(new RuntimeException());
        assertThrows(ResponseStatusException.class, () -> s.register(r));
    }
}
