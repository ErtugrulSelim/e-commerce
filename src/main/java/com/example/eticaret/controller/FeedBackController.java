package com.example.eticaret.controller;

import com.example.eticaret.dto.FeedBackDto;
import com.example.eticaret.model.User;
import com.example.eticaret.service.FeedBackService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/feedBack")
public class FeedBackController {
    private final FeedBackService feedBackService;

    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping("/comments/{productId}")
    public List<String> getFeedBackComments(@PathVariable long productId) {
        return feedBackService.getFeedBackComments(productId);
    }

    @GetMapping("/ratings/{productId}")
    public Float getFeedBackRatings(@PathVariable long productId) {
        return feedBackService.getFeedBackRating(productId);
    }

    @PostMapping("/add/{paymentId}")
    public ResponseEntity<String> addFeedBack(@Valid @AuthenticationPrincipal User user,
                                              @PathVariable long paymentId,
                                              @RequestBody FeedBackDto feedBackdto) {
        feedBackService.setFeedBack(user, feedBackdto, paymentId);
        return ResponseEntity.ok("FeedBack added");
    }

    @DeleteMapping("/delete-comment/{feedBackId}")
    public ResponseEntity<String> deleteFeedBackComment(@AuthenticationPrincipal @PathVariable long feedBackId) {
        feedBackService.deleteFeedBackComment(feedBackId);
        return ResponseEntity.ok("Comment deleted");
    }

    @DeleteMapping("/delete/{feedBackId}")
    public ResponseEntity<String> deleteFeedBack(@AuthenticationPrincipal @PathVariable long feedBackId) {
        feedBackService.deleteFeedBack(feedBackId);
        return ResponseEntity.ok("FeedBack deleted");
    }

    @PostMapping("/update/{paymentId}")
    public ResponseEntity<String> updateFeedBack(@Valid @AuthenticationPrincipal User user,
                                                 @PathVariable long paymentId,
                                                 @RequestBody FeedBackDto feedBackDto) {
        feedBackService.updateFeedBack(user, feedBackDto, paymentId);
        return ResponseEntity.ok("FeedBack updated");
    }
}
