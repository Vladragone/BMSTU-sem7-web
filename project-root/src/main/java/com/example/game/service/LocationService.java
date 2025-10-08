package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.repository.LocationRepository;
import com.example.game.service.interfaces.ILocationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return locationRepository.findDistinctNames();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching location names", e);
        }
    }

    @Override
    public List<Location> getAllLocations() {
        try {
            return locationRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching locations", e);
        }
    }

    @Override
    public Location addLocation(Location location) {
        try {
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating location", e);
        }
    }

    @Override
    public Location getRandomLocationByName(String name) {
        try {
            List<Location> locations = locationRepository.findByName(name);
            return locations.isEmpty() ? null : locations.get(random.nextInt(locations.size()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching random location", e);
        }
    }

    @Override
    public void deleteLocation(Long id) {
        try {
            if (!locationRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Локация с id " + id + " не найдена");
            }
            locationRepository.deleteById(id);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при удалении локации", e);
        }
}

}
