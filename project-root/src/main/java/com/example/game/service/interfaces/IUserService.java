package com.example.game.service.interfaces;

import com.example.game.dto.UserRegistrationRequest;
import com.example.game.model.User;

public interface IUserService {
    User register(UserRegistrationRequest request);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
}
