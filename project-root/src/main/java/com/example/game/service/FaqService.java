package com.example.game.service.impl;

import com.example.game.dto.FaqUpdateRequest;
import com.example.game.model.Faq;
import com.example.game.repository.FaqRepository;
import com.example.game.service.interfaces.IFaqService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Optional<Faq> getFaqById(Long id) {
        return faqRepository.findById(id);
    }

    @Override
    public Faq saveFaq(Faq faq) {
        try {
            return faqRepository.save(faq);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving FAQ", e);
        }
    }

    @Override
    public Faq updateFaq(Long id, FaqUpdateRequest updates) {
        return faqRepository.findById(id).map(existing -> {
            if (updates.getQuestion() != null) {
                existing.setQuestion(updates.getQuestion());
            }
            if (updates.getAnswer() != null) {
                existing.setAnswer(updates.getAnswer());
            }
            try {
                return faqRepository.save(existing);
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error updating FAQ with id=" + id,
                        e
                );
            }
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "FAQ with id=" + id + " not found"
        ));
    }

    @Override
    public void deleteFaq(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ with id=" + id + " not found");
        }
        try {
            faqRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting FAQ", e);
        }
    }
}
