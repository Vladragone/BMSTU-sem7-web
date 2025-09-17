package com.example.game.controller;

import com.example.game.model.Faq;
import com.example.game.service.interfaces.IFaqService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FaqController {

    private final IFaqService faqService;

    public FaqController(IFaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    @GetMapping("/{id}")
    public Faq getFaqById(@PathVariable Long id) {
        return faqService.getFaqById(id)
                .orElseThrow(() -> new RuntimeException("FAQ с id=" + id + " не найден"));
    }

    @PostMapping
    public Faq createFaq(@RequestBody Faq faq) {
        return faqService.saveFaq(faq);
    }

    @PatchMapping("/{id}")
    public Faq updateFaqQuestion(@PathVariable Long id, @RequestBody Faq faq) {
        if (faq.getQuestion() == null) {
            throw new RuntimeException("Поле question обязательно для PATCH");
        }
        return faqService.updateFaqQuestion(id, faq.getQuestion());
    }

    @DeleteMapping("/{id}")
    public void deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
    }
}
