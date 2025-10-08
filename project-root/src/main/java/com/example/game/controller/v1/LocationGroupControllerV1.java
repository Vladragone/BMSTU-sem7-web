package com.example.game.controller.v1;

import com.example.game.model.LocationGroup;
import com.example.game.service.interfaces.ILocationGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location-groups")
public class LocationGroupControllerV1 {

    private final ILocationGroupService locationGroupService;

    public LocationGroupControllerV1(ILocationGroupService locationGroupService) {
        this.locationGroupService = locationGroupService;
    }

    @Operation(summary = "Получить все группы локаций")
    @GetMapping
    public ResponseEntity<List<LocationGroup>> getAllGroups() {
        List<LocationGroup> groups = locationGroupService.getAllGroups();
        if (groups.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Создать новую группу")
    @PostMapping
    public ResponseEntity<LocationGroup> createGroup(@RequestBody LocationGroup group) {
        LocationGroup saved = locationGroupService.addGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Получить группу по имени")
    @GetMapping("/{name}")
    public ResponseEntity<LocationGroup> getGroupByName(@PathVariable String name) {
        LocationGroup group = locationGroupService.getGroupByName(name);
        return ResponseEntity.ok(group);
    }
}
