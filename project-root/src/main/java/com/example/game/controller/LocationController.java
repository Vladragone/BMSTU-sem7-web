package com.example.game.controller;

import com.example.game.model.Location;
import com.example.game.service.interfaces.ILocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllLocationNames() {
        return ResponseEntity.ok(locationService.getDistinctLocationNames());
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location created = locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/random")
    public ResponseEntity<Location> getRandomLocation(@RequestParam String name) {
        Location randomLocation = locationService.getRandomLocationByName(name);
        if (randomLocation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(randomLocation);
    }
}
