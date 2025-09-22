package com.example.game.service.interfaces;

import com.example.game.dto.RegistrationRequest;
import com.example.game.model.User;

public interface IRegistrationService {
    User register(RegistrationRequest request);
}
