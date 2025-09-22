package com.example.game.controller;

import com.example.game.dto.RegistrationRequest;
import com.example.game.model.User;
import com.example.game.service.interfaces.IRegistrationService;
import com.example.game.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;
    private final IRegistrationService registrationService;

    public UserController(IUserService userService, IRegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        User createdUser = registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
