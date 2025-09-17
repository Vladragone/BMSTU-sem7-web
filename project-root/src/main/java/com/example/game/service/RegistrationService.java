package com.example.game.service;

import com.example.game.dto.RegistrationRequest;
import com.example.game.model.Profile;
import com.example.game.model.User;
import com.example.game.repository.ProfileRepository;
import com.example.game.repository.UserRepository;
import com.example.game.service.interfaces.IRegistrationService;
import com.example.game.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private IUserService userService;

    @Override
    public void register(RegistrationRequest request) {
        try {
            if (userService.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Пользователь с таким именем уже существует");
            }

            if (userService.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Пользователь с таким email уже существует");
            }

            String hashedPassword = hashPassword(request.getPassword());

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(hashedPassword);
            user.setEmail(request.getEmail());
            user.setRole("user");

            User savedUser = userRepository.save(user);

            Profile profile = new Profile(savedUser, LocalDateTime.now());
            profileRepository.save(profile);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
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
            throw new RuntimeException("Ошибка при хешировании пароля", e);
        }
    }
}
