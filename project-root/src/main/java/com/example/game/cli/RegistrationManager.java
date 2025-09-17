package com.example.game.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.game.dto.RegistrationRequest;
import com.example.game.service.interfaces.IRegistrationService;
import java.util.Scanner;

@Component
public class RegistrationManager {

    private final IRegistrationService registrationService;

    @Autowired
    public RegistrationManager(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);

        registrationService.register(request);
    }
}
