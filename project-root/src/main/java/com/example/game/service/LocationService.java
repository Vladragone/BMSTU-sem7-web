package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.repository.LocationRepository;
import com.example.game.service.interfaces.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<String> getDistinctLocationNames() {
        return locationRepository.findDistinctNames();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
