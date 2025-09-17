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

    @PostMapping
    public Faq createFaq(@RequestBody Faq faq) {
        return faqService.saveFaq(faq);
    }
}
