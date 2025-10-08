package com.example.game.controller.v1;

import com.example.game.model.Location;
import com.example.game.model.LocationGroup;
import com.example.game.service.interfaces.ILocationService;
import com.example.game.service.interfaces.ILocationGroupService;
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
    private final ILocationGroupService locationGroupService;

    public LocationControllerV1(ILocationService locationService, ILocationGroupService locationGroupService) {
        this.locationService = locationService;
        this.locationGroupService = locationGroupService;
    }

    @Operation(summary = "Получить все локации")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Локации успешно получены"),
            @ApiResponse(responseCode = "204", description = "Локации отсутствуют")
    })
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        if (locations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Получить все локации в группе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Локации группы успешно получены"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    @GetMapping("/group/{groupName}")
    public ResponseEntity<List<Location>> getLocationsByGroup(@PathVariable String groupName) {
        LocationGroup group = locationGroupService.getGroupByName(groupName);
        List<Location> locations = locationService.getLocationsByGroup(group);
        if (locations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Добавить новую локацию")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Локация создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location saved = locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Получить случайную локацию по группе")
    @GetMapping("/random/{groupName}")
    public ResponseEntity<Location> getRandomByGroup(@PathVariable String groupName) {
        LocationGroup group = locationGroupService.getGroupByName(groupName);
        Location random = locationService.getRandomLocationByGroup(group);
        if (random == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(random);
    }

    @Operation(summary = "Удалить локацию")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
