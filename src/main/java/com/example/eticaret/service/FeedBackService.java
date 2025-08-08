package com.example.eticaret.service;


import com.example.eticaret.dto.FeedBackDto;
import com.example.eticaret.exceptions.*;
import com.example.eticaret.model.*;
import com.example.eticaret.repository.FeedBackRepository;
import com.example.eticaret.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.eticaret.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class FeedBackService {
    private PaymentRepository paymentRepository;
    private FeedBackRepository feedBackRepository;
    private ProductRepository productRepository;

    public float getFeedBackRating(long productId) {
        FeedBack feedBack = feedBackRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("There is no FeedBack with product_id " + productId));
        return feedBack.getRating();
    }

    public List<String> getFeedBackComments(long productId) {
        List<FeedBack> feedbackList = feedBackRepository.findByProduct_Id(productId);
        return feedbackList.stream()
                .map(fb -> fb.getUser().getUsername() + ": " + fb.getComment())
                .collect(Collectors.toList());
    }

    private FeedBack createFeedBack(User user, Payment payment) {
        FeedBack newFeedBack = new FeedBack();
        newFeedBack.setUser(user);
        newFeedBack.setProduct(payment.getProduct());
        newFeedBack.setPayment(payment);
        newFeedBack.setRating(1);
        return feedBackRepository.save(newFeedBack);
    }

    @Transactional
    public void setFeedBack(User user, FeedBackDto feedBackDto, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        FeedBack feedBack = feedBackRepository.findByPaymentId(paymentId);
        if (feedBack != null) {
            throw new FeedBackException("You have already added a feedBack");
        } else {
            FeedBack newFeedBack = createFeedBack(user, payment);
            newFeedBack.setRating(feedBackDto.getRating());
            newFeedBack.setComment(feedBackDto.getComment());

            feedBackRepository.save(newFeedBack);

            setAverageRating(feedBack.getPayment().getId());
        }
    }

    private void setAverageRating(long productId) {
        int feedBackScoreCount = feedBackRepository.getTotalCount(productId);
        int sumScore = feedBackRepository.getTotalScore(productId);
        float averageRating = (float) sumScore / feedBackScoreCount;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("There is no product with id " + productId));
        product.setAverageRating(averageRating);
        productRepository.save(product);
    }
}
