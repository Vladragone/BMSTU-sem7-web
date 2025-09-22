package com.example.game.service;

import com.example.game.model.Feedback;
import com.example.game.repository.FeedbackRepository;
import com.example.game.service.interfaces.IFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
}
