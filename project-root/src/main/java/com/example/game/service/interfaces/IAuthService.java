package com.example.game.service.interfaces;

import com.example.game.dto.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
