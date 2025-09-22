package com.example.game.service;

import com.example.game.dto.LoginRequest;
import com.example.game.dto.TokenResponse;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import com.example.game.service.interfaces.IAuthService;
import com.example.game.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
            }

            User user = userOptional.get();
            String hashedInputPassword = hashPassword(loginRequest.getPassword());

            if (!user.getPassword().equals(hashedInputPassword)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
            }

            String token = JwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
            return new TokenResponse(token);

        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal authentication error", ex);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error hashing password", e);
        }
    }
}
