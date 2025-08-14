package com.example.eticaret.controller;

import com.example.eticaret.dto.FeedbackDto;
import com.example.eticaret.model.User;
import com.example.eticaret.service.FeedbackService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {
    private final FeedbackService feedBackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedBackService = feedbackService;
    }

    @GetMapping("/comments/{productId}")
    public List<String> getFeedBackComments(@PathVariable long productId) {
        return feedBackService.getFeedbackComments(productId);
    }

    @GetMapping("/ratings/{productId}")
    public List<String> getFeedBackRatings(@PathVariable long productId) {
        return feedBackService.getFeedbackRating(productId);
    }

    @PostMapping("/add/{paymentId}")
    public ResponseEntity<String> addFeedBack(@Valid @AuthenticationPrincipal User user,
                                              @PathVariable long paymentId,
                                              @RequestBody FeedbackDto feedbackdto) {
        feedBackService.setFeedback(user, feedbackdto, paymentId);
        return ResponseEntity.ok("FeedBack added");
    }

    @DeleteMapping("/delete-comment/{feedBackId}")
    public ResponseEntity<String> deleteFeedBackComment(@AuthenticationPrincipal @PathVariable long feedBackId) {
        feedBackService.deleteFeedbackComment(feedBackId);
        return ResponseEntity.ok("Comment deleted");
    }

    @DeleteMapping("/delete/{feedBackId}")
    public ResponseEntity<String> deleteFeedBack(@AuthenticationPrincipal @PathVariable long feedBackId) {
        feedBackService.deleteFeedback(feedBackId);
        return ResponseEntity.ok("FeedBack deleted");
    }

    @PostMapping("/update/{paymentId}")
    public ResponseEntity<String> updateFeedBack(@Valid @AuthenticationPrincipal User user,
                                                 @PathVariable long paymentId,
                                                 @RequestBody FeedbackDto feedBackDto) {
        feedBackService.updateFeedback(user, feedBackDto, paymentId);
        return ResponseEntity.ok("FeedBack updated");
    }
}
