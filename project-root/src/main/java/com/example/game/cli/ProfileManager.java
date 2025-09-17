package com.example.game.cli;

import com.example.game.model.Profile;
import com.example.game.service.interfaces.IProfileService;
import com.example.game.service.interfaces.ITokenParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ProfileManager {

    private final IProfileService profileService;
    private final ITokenParser tokenParser;
    private final AuthManager authManager;
    private final RatingManager ratingManager;
    private final GameManager gameManager;

    public ProfileManager(IProfileService profileService,
                          ITokenParser tokenParser,
                          AuthManager authManager,
                          RatingManager ratingManager,
                          GameManager gameManager) {
        this.profileService = profileService;
        this.tokenParser = tokenParser;
        this.authManager = authManager;
        this.ratingManager = ratingManager;
        this.gameManager = gameManager;
    }

    public void showProfile() {
        String token = authManager.getAuthToken();

        if (token == null) {
            System.out.println("You are not logged in");
            return;
        }

        try {
            String username = tokenParser.getUsername(token);
            Profile profile = profileService.getProfile(username);

            if (profile == null) {
                System.out.println("Profile not found");
                return;
            }

            System.out.println("===== Profile Statistics =====");
            System.out.println("Username: " + profile.getUser().getUsername());
            System.out.println("Score: " + profile.getScore());
            System.out.println("Games played: " + profile.getGameNum());

        } catch (Exception e) {
            System.out.println("Failed to fetch profile: " + e.getMessage());
        }
    }

    public void profileMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showProfile();

            System.out.println("\nProfile Menu:");
            System.out.println("1. Rating");
            System.out.println("2. Start game");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    ratingManager.showRatingMenu();
                    break;
                case "2":
                    gameManager.startGame();
                    break;
                case "3":
                    authManager.resetToken();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
