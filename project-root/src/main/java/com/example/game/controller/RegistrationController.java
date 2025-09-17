package com.example.game.controller;

import com.example.game.dto.RegistrationRequest;
import com.example.game.service.interfaces.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final IRegistrationService registrationService;

    @Autowired
    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        registrationService.register(request);
        return ResponseEntity.ok("Регистрация успешна");
    }
}
