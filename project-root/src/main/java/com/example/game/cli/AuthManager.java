package com.example.game.cli;

import com.example.game.service.AuthService;
import com.example.game.dto.LoginRequest;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class AuthManager {

    private final AuthService authService;
    private String authToken;

    public AuthManager(AuthService authService) {
        this.authService = authService;
    }

    public boolean authenticate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        try {
            var response = authService.authenticateUser(loginRequest);

            @SuppressWarnings("unchecked")
            Map<String, Object> body = (Map<String, Object>) response.getBody();

            System.out.println("Authentication successful!");

            String token = body.get("token").toString();
            this.authToken = token;
            System.out.println(this.authToken);

            return true;
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public void resetToken() {
        this.authToken = null;
    }
}
