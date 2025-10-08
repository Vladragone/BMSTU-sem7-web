package com.example.game.service.interfaces;

import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
import java.util.List;

public interface ILocationService {

    List<Location> getAllLocations();
    List<Location> getLocationsByGroup(LocationGroup group);
    Location addLocation(Location location);
    Location getRandomLocationByGroup(LocationGroup group);
    void deleteLocation(Long id);
}
