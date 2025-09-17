package com.example.game.service.interfaces;

import com.example.game.model.Location;

import java.util.List;

public interface ILocationService {
    List<String> getDistinctLocationNames();
    List<Location> getAllLocations();
}
