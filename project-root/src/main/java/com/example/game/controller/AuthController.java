package com.example.game.controller;

import com.example.game.dto.LoginRequest;
import com.example.game.dto.TokenResponse;
import com.example.game.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tokens")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(token);
    }
}
