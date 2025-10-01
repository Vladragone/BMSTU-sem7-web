package com.example.game.controller.v1;

import com.example.game.model.Profile;
import com.example.game.dto.ProfileUpdateRequest;
import com.example.game.service.interfaces.IProfileService;
import com.example.game.service.interfaces.ITokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileControllerV1 {

    private final IProfileService profileService;
    private final ITokenParser tokenParser;

    @Autowired
    public ProfileControllerV1(IProfileService profileService, ITokenParser tokenParser) {
        this.profileService = profileService;
        this.tokenParser = tokenParser;
    }

    @Operation(summary = "Получить профиль текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль найден"),
            @ApiResponse(responseCode = "401", description = "Неавторизован"),
            @ApiResponse(responseCode = "404", description = "Профиль не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/me")
    public ResponseEntity<Profile> getMyProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = tokenParser.getUsername(token);
        Profile profile = profileService.getProfile(username);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Обновить профиль текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль обновлён"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "401", description = "Неавторизован"),
            @ApiResponse(responseCode = "404", description = "Профиль не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PatchMapping("/me")
    public ResponseEntity<Profile> updateMyProfile(
            @RequestBody ProfileUpdateRequest updates,
            HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = tokenParser.getUsername(token);
        Profile updatedProfile = profileService.updateProfile(updates, username);

        if (updatedProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updatedProfile);
    }
}
