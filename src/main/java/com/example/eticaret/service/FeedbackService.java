package com.example.eticaret.service;


import com.example.eticaret.dto.FeedbackDto;
import com.example.eticaret.exceptions.*;
import com.example.eticaret.model.*;
import com.example.eticaret.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class FeedbackService {
    private PaymentRepository paymentRepository;
    private FeedbackRepository feedbackRepository;
    private ProductRepository productRepository;

    private List<FeedbackDto> getFeedBackDto(long productId) {
        if (!productRepository.existsById(productId)) return Collections.emptyList();

        List<Feedback> feedbackList = feedbackRepository.findByProduct_Id(productId);

        return feedbackList.stream().map(fb -> {
            FeedbackDto dto = new FeedbackDto();
            dto.setId(fb.getId());
            dto.setComment(fb.getComment());
            dto.setRating(fb.getRating());
            dto.setUserId(fb.getUser() != null ? fb.getUser().getId() : null);
            dto.setProductId(fb.getProduct() != null ? fb.getProduct().getId() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<String> getFeedbackRating(long productId) {
        return getFeedBackDto(productId).stream()
                .map(dto -> dto.getUserId() + ": " + dto.getRating())
                .collect(Collectors.toList());
    }

    public List<String> getFeedbackComments(long productId) {
        List<FeedbackDto> feedBackDto = getFeedBackDto(productId);

        return feedBackDto.stream()
                .map(dto -> dto.getUserId() + ": " + dto.getComment())
                .collect(Collectors.toList());
    }


    @Transactional
    public void setFeedback(User user, FeedbackDto feedbackDto, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        Feedback feedback = feedbackRepository.findByPaymentId(paymentId);
        if (feedback != null) {
            throw new FeedbackException("You have already added a feedBack");
        } else {
            Feedback newFeedback = createFeedback(user, payment);
            newFeedback.setRating(feedbackDto.getRating());
            newFeedback.setComment(feedbackDto.getComment());

            feedbackRepository.save(newFeedback);

            setAverageRating(newFeedback.getProduct().getId());
        }
    }

    @Transactional
    public void updateFeedback(User user, FeedbackDto feedbackDto, long paymentId) {
        Feedback feedback = feedbackRepository.findByPaymentIdAndUser(paymentId, user);
        if (feedback == null) {
            throw new NotFoundException("There is no FeedBack with product_id " + feedbackDto.getProductId());
        } else {
            feedback.setRating(feedbackDto.getRating());
            feedback.setComment(feedbackDto.getComment());
            feedbackRepository.save(feedback);
            setAverageRating(feedback.getProduct().getId());
        }
    }

    public void deleteFeedbackComment(long feedBackId) {
        Feedback feedback = feedbackRepository.findById(feedBackId)
                .orElseThrow(() -> new NotFoundException("There is no FeedBack this id: " + feedBackId));
        feedback.setComment(null);
        feedbackRepository.save(feedback);
    }

    public void deleteFeedback(long feedBackId) {
        Feedback feedback = feedbackRepository.findById(feedBackId)
                .orElseThrow(() -> new NotFoundException("There is no FeedBack this id: " + feedBackId));
        feedbackRepository.delete(feedback);
    }

    private Feedback createFeedback(User user, Payment payment) {
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setProduct(payment.getProduct());
        feedback.setPayment(payment);
        feedback.setRating(3);
        return feedbackRepository.save(feedback);
    }

    private void setAverageRating(long productId) {
        int feedbackScoreCount = feedbackRepository.getTotalCount(productId);
        int sumScore = feedbackRepository.getTotalScore(productId);
        float averageRating = (float) sumScore / feedbackScoreCount;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("There is no product with id " + productId));
        product.setAverageRating(averageRating);
        productRepository.save(product);
    }
}
