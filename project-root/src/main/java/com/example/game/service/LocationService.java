package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.repository.LocationRepository;
import com.example.game.service.interfaces.ILocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final Random random = new Random();

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<String> getDistinctLocationNames() {
        return locationRepository.findDistinctNames();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location getRandomLocationByName(String name) {
        List<Location> locations = locationRepository.findByName(name);
        return locations.isEmpty() ? null : locations.get(random.nextInt(locations.size()));
    }
}
