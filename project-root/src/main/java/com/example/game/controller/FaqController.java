package com.example.game.controller;

import com.example.game.model.Faq;
import com.example.game.service.interfaces.IFaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faqs")
public class FaqController {

    private final IFaqService faqService;

    public FaqController(IFaqService faqService) {
        this.faqService = faqService;
    }

    @Operation(summary = "Получить список всех FAQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ успешно получены"),
            @ApiResponse(responseCode = "204", description = "FAQ отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaqs() {
        List<Faq> faqs = faqService.getAllFaqs();
        if (faqs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(faqs);
    }

    @Operation(summary = "Получить FAQ по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ найден"),
            @ApiResponse(responseCode = "404", description = "FAQ не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Faq> getFaqById(@PathVariable Long id) {
        return faqService.getFaqById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Создать новый FAQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FAQ создан"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
        Faq created = faqService.saveFaq(faq);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Обновить существующий FAQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ обновлён"),
            @ApiResponse(responseCode = "404", description = "FAQ не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Faq> updateFaq(@PathVariable Long id, @RequestBody Faq faq) {
        Faq updated = faqService.updateFaq(id, faq);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Удалить FAQ по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "FAQ удалён"),
            @ApiResponse(responseCode = "404", description = "FAQ не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }
}
