package com.example.game.service.interfaces;

import com.example.game.model.Profile;

import java.util.Map;

public interface IProfileService {
    Profile getProfile(String username);
    Profile updateProfile(Map<String, Object> updates, String username);
}
