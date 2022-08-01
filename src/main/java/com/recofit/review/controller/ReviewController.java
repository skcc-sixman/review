package com.recofit.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recofit.review.domain.Review;
import com.recofit.review.event.ReviewCreated;
import com.recofit.review.service.EventService;
import com.recofit.review.service.ReviewService;
import com.recofit.review.util.TargetType;

@RestController
@CrossOrigin
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private EventService eventService;

  @PostMapping(value = "/reviews")
  public void createReview(@RequestBody Review review) {
    reviewService.createReview(review);

    ReviewCreated reviewCreated = new ReviewCreated(review);

    String event = reviewCreated.json();

    eventService.publish("test", event);
  }

  @GetMapping(value = "/reviews")
  public List<Review> getReviews() {
    return reviewService.getReviews();
  }

  @GetMapping(value = "/reviews/{reviewId}")
  public Review getReview(@PathVariable Long reviewId) {
    return reviewService.getReview(reviewId);
  }

}
