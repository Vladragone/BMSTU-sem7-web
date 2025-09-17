package com.example.game.cli;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI {

    private final AuthManager authManager;
    private final RegistrationManager registrationManager;
    private final GameManager gameManager;
    private final ProfileManager profileManager;
    private final RatingManager ratingManager;

    public ConsoleUI(AuthManager authManager,
                     RegistrationManager registrationManager,
                     GameManager gameManager,
                     ProfileManager profileManager,
                     RatingManager ratingManager) {
        this.authManager = authManager;
        this.registrationManager = registrationManager;
        this.gameManager = gameManager;
        this.profileManager = profileManager;
        this.ratingManager = ratingManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    if (authManager.authenticate()) {
                        profileManager.profileMenu();
                    }
                    break;
                case "2":
                    registrationManager.registerUser();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
