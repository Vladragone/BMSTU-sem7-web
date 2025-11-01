package com.example.game.controller.v1;

import com.example.game.dto.GameRoundRequestDTO;
import com.example.game.dto.GameRoundResponseDTO;
import com.example.game.model.GameRound;
import com.example.game.service.interfaces.IGameRoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gamerounds")
public class GameRoundControllerV1 {

    private final IGameRoundService gameRoundService;

    public GameRoundControllerV1(IGameRoundService gameRoundService) {
        this.gameRoundService = gameRoundService;
    }

    @Operation(summary = "Получить раунды", description = "Получить все раунды или отфильтровать по сессии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Раунды успешно получены"),
            @ApiResponse(responseCode = "204", description = "Раунды отсутствуют")
    })
    @GetMapping
    public ResponseEntity<List<GameRoundResponseDTO>> getRounds(
            @Parameter(description = "ID сессии для фильтрации")
            @RequestParam(required = false) Long sessionId,
            @Parameter(description = "Получить только последний раунд")
            @RequestParam(required = false) Boolean last) {
        
        List<GameRound> rounds;
        
        if (sessionId != null && Boolean.TRUE.equals(last)) {
            GameRound lastRound = gameRoundService.getCurrentRoundBySessionId(sessionId);
            if (lastRound == null) {
                return ResponseEntity.noContent().build();
            }
            rounds = List.of(lastRound);
        } else if (sessionId != null) {
            rounds = gameRoundService.getRoundsBySessionId(sessionId);
        } else {
            rounds = gameRoundService.getAllRounds();
        }
        
        if (rounds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<GameRoundResponseDTO> dtos = rounds.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Создать новый раунд")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Раунд успешно создан"),
            @ApiResponse(responseCode = "500", description = "Ошибка при сохранении раунда")
    })
    @PostMapping
    public ResponseEntity<GameRoundResponseDTO> createRound(@RequestBody GameRoundRequestDTO dto) {
        GameRound saved = gameRoundService.createFromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(saved));
    }

    @Operation(summary = "Получить раунд по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Раунд найден"),
            @ApiResponse(responseCode = "404", description = "Раунд не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameRoundResponseDTO> getRoundById(@PathVariable Long id) {
        return gameRoundService.getRoundById(id)
                .map(round -> ResponseEntity.ok(toResponseDto(round)))
                .orElse(ResponseEntity.notFound().build());
    }

    private GameRoundResponseDTO toResponseDto(GameRound entity) {
        GameRoundResponseDTO dto = new GameRoundResponseDTO();
        dto.setId(entity.getId());
        dto.setSessionId(entity.getSession().getId());
        dto.setLocationId(entity.getLocation().getId());
        dto.setGuessLat(entity.getGuessLat());
        dto.setGuessLng(entity.getGuessLng());
        dto.setScore(entity.getScore());
        dto.setRoundNumber(entity.getRoundNumber());
        return dto;
    }
}