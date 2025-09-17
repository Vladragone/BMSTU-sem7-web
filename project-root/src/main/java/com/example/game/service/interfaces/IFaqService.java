package com.example.game.service.interfaces;

import com.example.game.model.Faq;
import java.util.List;

public interface IFaqService {
    List<Faq> getAllFaqs();
    Faq saveFaq(Faq faq);
}
