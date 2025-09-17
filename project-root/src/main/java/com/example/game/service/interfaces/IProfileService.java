package com.example.game.service.interfaces;

import com.example.game.model.Profile;

import java.util.Map;

public interface IProfileService {
    Profile getProfile(String username);
    Map<String, Object> updateScore(Map<String, Integer> scoreMap, String username);
}
