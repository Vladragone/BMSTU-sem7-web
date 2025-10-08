package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
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
    public List<Location> getAllLocations() {
        try {
            return locationRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении списка локаций", e);
        }
    }

    @Override
    public List<Location> getLocationsByGroup(LocationGroup group) {
        try {
            return locationRepository.findByGroup(group);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении локаций группы", e);
        }
    }

    @Override
    public Location addLocation(Location location) {
        try {
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при создании локации", e);
        }
    }

    @Override
    public Location getRandomLocationByGroup(LocationGroup group) {
        try {
            List<Location> locations = locationRepository.findByGroup(group);
            if (locations.isEmpty()) return null;
            return locations.get(random.nextInt(locations.size()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при выборе случайной локации", e);
        }
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Локация с id " + id + " не найдена");
        }
        locationRepository.deleteById(id);
    }
}
