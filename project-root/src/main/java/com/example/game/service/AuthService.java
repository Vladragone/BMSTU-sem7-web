package com.example.game.service;

import com.example.game.dto.LoginRequest;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import com.example.game.util.JwtUtil;
import com.example.game.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedInputPassword = hashPassword(loginRequest.getPassword());

            if (user.getPassword().equals(hashedInputPassword)) {
                String token = JwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Authentication successful");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
    }

    String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
