package com.example.game.cli;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner {

    private final ConsoleUI consoleUI;

    public ConsoleRunner(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        consoleUI.start();
    }
}
