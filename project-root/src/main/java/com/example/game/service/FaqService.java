package com.example.game.service;

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
        try {
            return faqRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching FAQs", e);
        }
    }

    @Override
    public Faq saveFaq(Faq faq) {
        try {
            return faqRepository.save(faq);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating FAQ", e);
        }
    }

    @Override
    public Optional<Faq> getFaqById(Long id) {
        try {
            return faqRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching FAQ by id", e);
        }
    }

    @Override
    public Faq updateFaq(Long id, Faq updates) {
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
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating FAQ", e);
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ with id=" + id + " not found"));
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
