package com.example.game.cli;

import com.example.game.dto.RatingResponse;
import com.example.game.model.Profile;
import com.example.game.service.interfaces.IRatingService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class RatingManager {

    private final IRatingService ratingService;
    private final AuthManager authManager;

    public RatingManager(IRatingService ratingService, AuthManager authManager) {
        this.ratingService = ratingService;
        this.authManager = authManager;
    }

    public void showRatingMenu() {
        Scanner scanner = new Scanner(System.in);

        String token = authManager.getAuthToken();
        if (token == null) {
            System.out.println("You must be logged in to see the rating.");
            return;
        }

        RatingResponse scoreRating = ratingService.getSortedRatingAndRank(token, "score");
        RatingResponse gamesRating = ratingService.getSortedRatingAndRank(token, "games");

        System.out.println("\n--- Top 3 by Score ---");
        printTopProfiles(scoreRating.getTop());

        System.out.println("\n--- Top 3 by Games ---");
        printTopProfiles(gamesRating.getTop());

        System.out.println("\nYour rank by " + scoreRating.getSortBy() + ": " + scoreRating.getYourRank());
        System.out.println("Your rank by " + gamesRating.getSortBy() + ": " + gamesRating.getYourRank());

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Profile");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void printTopProfiles(List<Profile> profiles) {
        int place = 1;
        for (Profile profile : profiles) {
            System.out.println(place + ". " + profile.getUser().getUsername() +
                    " - Score: " + profile.getScore() +
                    ", Games: " + profile.getGameNum());
            place++;
        }
    }
}
