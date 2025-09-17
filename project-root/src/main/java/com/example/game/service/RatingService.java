package com.example.game.service;

import com.example.game.dto.RatingResponse;
import com.example.game.model.Profile;
import com.example.game.repository.ProfileRepository;
import com.example.game.service.interfaces.IRatingService;
import com.example.game.service.interfaces.ITokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService implements IRatingService {

    private final ProfileRepository profileRepository;
    private final ITokenParser tokenParser;

    @Autowired
    public RatingService(ProfileRepository profileRepository, ITokenParser tokenParser) {
        this.profileRepository = profileRepository;
        this.tokenParser = tokenParser;
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getSortedRatingAndRank(String token, String sortBy) {
        String currentUsername = tokenParser.getUsername(token);
        List<Profile> profiles = profileRepository.findAll();

        Comparator<Profile> comparator = getComparator(sortBy);
        List<Profile> sortedProfiles = profiles.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());

        List<Profile> top = sortedProfiles.stream()
                .limit(3)
                .collect(Collectors.toList());

        int rank = findUserRank(sortedProfiles, currentUsername);

        return new RatingResponse(top, rank, sortBy);
    }

    private Comparator<Profile> getComparator(String sortBy) {
        return "games".equals(sortBy)
                ? Comparator.comparingInt(Profile::getGameNum)
                : Comparator.comparingInt(Profile::getScore);
    }

    private int findUserRank(List<Profile> profiles, String username) {
        return profiles.stream()
                .map(profile -> profile.getUser().getUsername())
                .toList()
                .indexOf(username) + 1;
    }
}
