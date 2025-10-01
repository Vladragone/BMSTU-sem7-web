package com.example.game.controller.v1;

import com.example.game.model.Location;
import com.example.game.service.interfaces.ILocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationControllerV1 {

    private final ILocationService locationService;

    public LocationControllerV1(ILocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Получить список всех локаций")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Локации успешно получены"),
            @ApiResponse(responseCode = "204", description = "Локации отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        if (locations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Получить список всех уникальных имён локаций")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Названия успешно получены"),
            @ApiResponse(responseCode = "204", description = "Названия отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllLocationNames() {
        List<String> names = locationService.getDistinctLocationNames();
        if (names.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(names);
    }

    @Operation(summary = "Создать новую локацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Локация успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location created = locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Получить случайную локацию по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Локация найдена"),
            @ApiResponse(responseCode = "404", description = "Локации с таким именем не существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/random")
    public ResponseEntity<Location> getRandomLocation(@RequestParam String name) {
        Location randomLocation = locationService.getRandomLocationByName(name);
        if (randomLocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(randomLocation);
    }
}
