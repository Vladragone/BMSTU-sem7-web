package com.example.game.service.interfaces;

import com.example.game.model.Faq;
import java.util.List;
import java.util.Optional;

public interface IFaqService {
    List<Faq> getAllFaqs();
    Faq saveFaq(Faq faq);
    Optional<Faq> getFaqById(Long id);
    Faq updateFaqQuestion(Long id, String newQuestion);
    void deleteFaq(Long id);
}
