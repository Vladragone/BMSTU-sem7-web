package com.example.game.service;

import com.example.game.dto.RatingResponse;
import com.example.game.model.Profile;
import com.example.game.repository.ProfileRepository;
import com.example.game.service.interfaces.IRatingService;
import com.example.game.service.interfaces.ITokenParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService implements IRatingService {

    private final ProfileRepository profileRepository;
    private final ITokenParser tokenParser;

    public RatingService(ProfileRepository profileRepository, ITokenParser tokenParser) {
        this.profileRepository = profileRepository;
        this.tokenParser = tokenParser;
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getSortedRatingAndRank(String token, String sortBy, int limit) {
        try {
            String currentUsername = tokenParser.getUsername(token);
            List<Profile> profiles = profileRepository.findAll();
            if (profiles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No profiles found");
            }

            Comparator<Profile> comparator = getComparator(sortBy);
            List<Profile> sortedProfiles = profiles.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());

            List<Profile> top = sortedProfiles.stream()
                    .limit(limit)
                    .collect(Collectors.toList());

            int rank = findUserRank(sortedProfiles, currentUsername);
            if (rank <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in rating");
            }

            return new RatingResponse(top, rank, sortBy);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching ratings", e);
        }
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
