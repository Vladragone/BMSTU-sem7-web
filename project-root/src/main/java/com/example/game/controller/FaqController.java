package com.example.game.controller;

import com.example.game.model.Faq;
import com.example.game.service.interfaces.IFaqService;
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

    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaqs() {
        return ResponseEntity.ok(faqService.getAllFaqs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faq> getFaqById(@PathVariable Long id) {
        return faqService.getFaqById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
        Faq created = faqService.saveFaq(faq);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Faq> updateFaq(@PathVariable Long id, @RequestBody Faq faq) {
        Faq updated = faqService.updateFaq(id, faq);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }
}
