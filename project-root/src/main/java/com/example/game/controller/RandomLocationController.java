package com.example.game.controller;

import com.example.game.model.Location;
import com.example.game.service.interfaces.IRandomLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class RandomLocationController {

    private final IRandomLocationService randomLocationService;

    @Autowired
    public RandomLocationController(IRandomLocationService randomLocationService) {
        this.randomLocationService = randomLocationService;
    }

    @GetMapping("/random")
    public ResponseEntity<Location> getRandomLocation(@RequestParam String name) {
        return ResponseEntity.ok(randomLocationService.getRandomLocationByName(name));
    }
}
