package com.example.game.service.interfaces;

import com.example.game.dto.LoginRequest;
import com.example.game.dto.TokenResponse;

public interface IAuthService {
    TokenResponse authenticateUser(LoginRequest loginRequest);
}
