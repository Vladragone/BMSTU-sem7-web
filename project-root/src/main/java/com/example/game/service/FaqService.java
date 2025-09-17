package com.example.game.service;

import com.example.game.model.Faq;
import com.example.game.repository.FaqRepository;
import com.example.game.service.interfaces.IFaqService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaqService implements IFaqService {

    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    @Override
    public Faq saveFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    @Override
    public Optional<Faq> getFaqById(Long id) {
        return faqRepository.findById(id);
    }

    @Override
    public Faq updateFaqQuestion(Long id, String newQuestion) {
        return faqRepository.findById(id).map(existing -> {
            existing.setQuestion(newQuestion);
            return faqRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("FAQ с id=" + id + " не найден"));
    }

    @Override
    public void deleteFaq(Long id) {
        faqRepository.deleteById(id);
    }
}
