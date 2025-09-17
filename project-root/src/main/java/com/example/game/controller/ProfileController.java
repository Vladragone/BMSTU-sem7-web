package com.example.game.controller;

import com.example.game.model.Profile;
import com.example.game.service.interfaces.IProfileService;
import com.example.game.service.interfaces.ITokenParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final IProfileService profileService;
    private final ITokenParser tokenParser;

    @Autowired
    public ProfileController(IProfileService profileService, ITokenParser tokenParser) {
        this.profileService = profileService;
        this.tokenParser = tokenParser;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = tokenParser.getUsername(token);
        Profile profile = profileService.getProfile(username);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/score")
    public ResponseEntity<Map<String, Object>> updateScore(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = tokenParser.getUsername(token);
        return ResponseEntity.ok(profileService.updateScore(body, username));
    }
}
