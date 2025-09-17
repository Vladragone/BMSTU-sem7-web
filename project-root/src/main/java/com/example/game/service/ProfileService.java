package com.example.game.service;

import com.example.game.model.Profile;
import com.example.game.repository.ProfileRepository;
import com.example.game.service.interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile getProfile(String username) {
        return profileRepository.findByUserUsername(username).orElse(null);
    }

    @Override
    public Map<String, Object> updateScore(Map<String, Integer> scoreMap, String username) {
        Optional<Profile> optionalProfile = profileRepository.findByUserUsername(username);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            Integer additionalScore = scoreMap.getOrDefault("score", 0);
            profile.setScore(profile.getScore() + additionalScore);
            profile.setGameNum(profile.getGameNum() + 1);
            profileRepository.save(profile);
            return Map.of("totalScore", profile.getScore());
        }
        return Map.of("error", "Профиль не найден");
    }
}