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
    public ResponseEntity<List<String>> getFeedBackComments(@PathVariable long productId) {
        return ResponseEntity.ok(feedBackService.getFeedBackComments(productId));
    }

    @GetMapping("/ratings/{productId}")
    public ResponseEntity<Float> getFeedBackRatings(@PathVariable long productId) {
        return ResponseEntity.ok(feedBackService.getFeedBackRating(productId));
    }

    @PostMapping("/add{paymentId}")
    public ResponseEntity<String> addFeedBack(@Valid @AuthenticationPrincipal User user,
                                              @RequestParam long paymentId,
                                              @RequestBody FeedBackDto feedBackdto)
            {
        feedBackService.setFeedBack(user, feedBackdto, paymentId);
        return  ResponseEntity.ok("FeedBack added");
    }
}
