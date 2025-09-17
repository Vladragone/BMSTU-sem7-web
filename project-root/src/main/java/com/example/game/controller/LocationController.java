package com.example.game.controller;

import com.example.game.model.Location;
import com.example.game.service.interfaces.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final ILocationService locationService;

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllLocationNames() {
        return ResponseEntity.ok(locationService.getDistinctLocationNames());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
