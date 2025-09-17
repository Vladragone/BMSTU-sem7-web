package com.example.game.service;

import com.example.game.model.Location;
import com.example.game.repository.LocationRepository;
import com.example.game.service.interfaces.IRandomLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomLocationService implements IRandomLocationService {

    @Autowired
    private LocationRepository locationRepository;

    private final Random random = new Random();

    @Override
    public Location getRandomLocationByName(String name) {
        List<Location> locations = locationRepository.findByName(name);
        return locations.isEmpty() ? null : locations.get(random.nextInt(locations.size()));
    }
}
