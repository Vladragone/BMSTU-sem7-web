package com.example.game.controller;

import com.example.game.model.Location;
import com.example.game.service.interfaces.ILocationAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/locations")
public class LocationAdminController {

    private final ILocationAdminService locationAdminService;

    @Autowired
    public LocationAdminController(ILocationAdminService locationAdminService) {
        this.locationAdminService = locationAdminService;
    }

    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody Location location, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return ResponseEntity.ok(locationAdminService.addLocation(location, token));
    }
}
