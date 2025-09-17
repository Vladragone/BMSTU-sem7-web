package com.example.game.service;

import com.example.game.model.Feedback;
import com.example.game.repository.FeedbackRepository;
import com.example.game.service.interfaces.IFeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService implements IFeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");
        logger.info("--------");

        logger.info("Received feedback: {}", feedback);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        logger.info("Saved feedback with id: {}", savedFeedback.getId());

        return savedFeedback;
    }
}
