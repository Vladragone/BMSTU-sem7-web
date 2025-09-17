package com.example.game.cli;

import com.example.game.model.GameSession;
import com.example.game.model.Location;
import com.example.game.model.User;
import com.example.game.service.GameSessionService;
import com.example.game.service.LocationService;
import com.example.game.service.UserService;
import com.example.game.service.interfaces.ITokenParser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class GameManager {

    private final LocationService locationService;
    private final GameSessionService gameSessionService;
    private final AuthManager authManager;
    private final ITokenParser tokenParser;
    private final UserService userService;
    private final Scanner scanner;

    public GameManager(LocationService locationService,
                       GameSessionService gameSessionService,
                       AuthManager authManager,
                       ITokenParser tokenParser,
                       UserService userService) {
        this.locationService = locationService;
        this.gameSessionService = gameSessionService;
        this.authManager = authManager;
        this.tokenParser = tokenParser;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        List<Location> allLocations = locationService.getAllLocations();
        List<String> uniqueNames = allLocations.stream()
                .map(Location::getName)
                .distinct()
                .toList();

        int choice = -1;
        while (true) {
            System.out.println("Choose a location:");
            for (int i = 0; i < uniqueNames.size(); i++) {
                System.out.println((i + 1) + " - " + uniqueNames.get(i));
            }
            System.out.print("Enter the location number: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > uniqueNames.size()) {
                    System.out.println("Invalid choice");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        String selectedName = uniqueNames.get(choice - 1);
        Location selectedLocation = allLocations.stream()
                .filter(loc -> loc.getName().equals(selectedName))
                .findFirst()
                .orElseThrow();

        System.out.println("You chose: " + selectedLocation.getName());

        double userLat, userLng;
        while (true) {
            System.out.print("Enter coordinates (lat lng): ");
            String[] coords = scanner.nextLine().trim().split("\\s+");

            if (coords.length != 2) {
                System.out.println("Invalid input. Two numbers using space");
                continue;
            }

            try {
                userLat = Double.parseDouble(coords[0]);
                userLng = Double.parseDouble(coords[1]);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        }

        System.out.println("Original coordinates: " + selectedLocation.getLat() + " " + selectedLocation.getLng());
        System.out.println("Your coordinates: " + userLat + " " + userLng);

        Long userId = getUserIdFromToken();
        if (userId == null) {
            System.out.println("Не удалось определить пользователя. Сначала залогиньтесь.");
            return;
        }

        GameSession gameSession = new GameSession();
        gameSession.setUserId(userId);
        gameSession.setUserLat(userLat);
        gameSession.setUserLng(userLng);
        gameSession.setCorrectLat(selectedLocation.getLat());
        gameSession.setCorrectLng(selectedLocation.getLng());
        gameSession.setEarnedScore(0);

        gameSessionService.saveGameSession(gameSession);

        System.out.println("Game session saved!");
    }

    private Long getUserIdFromToken() {
        String token = authManager.getAuthToken();
        if (token == null) return null;

        try {
            String username = tokenParser.getUsername(token);
            User user = userService.findUserByUsername(username);
            if (user == null) {
                System.out.println("User not found by username: " + username);
                return null;
            }
            return user.getId();
        } catch (Exception e) {
            System.out.println("Failed to parse token or find user: " + e.getMessage());
            return null;
        }
    }
}
