package com.example.game.controller.v1;

import com.example.game.dto.LoginRequest;
import com.example.game.dto.TokenResponse;
import com.example.game.service.interfaces.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tokens")
public class AuthControllerV1 {

    private final IAuthService authService;

    @Autowired
    public AuthControllerV1(IAuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Создание JWT токена по логину и паролю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "401", description = "Неверные логин или пароль"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(token);
    }
}
