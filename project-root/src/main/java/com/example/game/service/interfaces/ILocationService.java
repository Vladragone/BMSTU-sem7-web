package com.example.game.service.interfaces;

import com.example.game.dto.LocationRequestDTO;
import com.example.game.model.Location;

import java.util.List;

public interface ILocationService {
    
    List<Location> getAllLocations();
    
    List<Location> getLocationsByGroupId(Long groupId);
    
    Location getLocationById(Long id);
    
    Location addLocationFromDto(LocationRequestDTO dto);
    
    Location updateLocationFromDto(Long id, LocationRequestDTO dto);
    
    void deleteLocation(Long id);
}