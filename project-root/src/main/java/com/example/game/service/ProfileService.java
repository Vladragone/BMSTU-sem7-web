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
    public Profile updateProfile(Map<String, Object> updates, String username) {
        Optional<Profile> optionalProfile = profileRepository.findByUserUsername(username);
        if (optionalProfile.isEmpty()) {
            return null;
        }

        Profile profile = optionalProfile.get();

        if (updates.containsKey("score")) {
            Integer additionalScore = (Integer) updates.get("score");
            profile.setScore(profile.getScore() + additionalScore);
            profile.setGameNum(profile.getGameNum() + 1);
        }
        return profileRepository.save(profile);
    }

}