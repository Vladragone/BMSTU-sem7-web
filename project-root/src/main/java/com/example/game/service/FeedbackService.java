package com.example.game.service;

import com.example.game.model.Feedback;
import com.example.game.repository.FeedbackRepository;
import com.example.game.service.interfaces.IFeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        try {
            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving feedback", e);
        }
    }
}
