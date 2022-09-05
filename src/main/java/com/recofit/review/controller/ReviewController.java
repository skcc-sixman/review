package com.recofit.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recofit.review.domain.Review;
import com.recofit.review.event.ReviewCreated;
import com.recofit.review.event.ReviewUpdated;
import com.recofit.review.service.EventService;
import com.recofit.review.service.ReviewService;
import com.recofit.review.util.TargetType;
import com.recofit.review.util.UserType;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private EventService eventService;

  @PostMapping(value = "/reviews")
  public void createReview(@RequestBody Review review) {
    reviewService.setReview(review);

    ReviewCreated reviewCreated = new ReviewCreated(review);

    String event = reviewCreated.json();

    eventService.publish("review", event);
  }

  @GetMapping(value = "/reviews")
  public List<Review> getReviews(@RequestParam(required = false, value = "target-type") TargetType targetType) {
    Long userId = 1L;
    UserType userType = UserType.USER;

    if(userType == UserType.USER) {
      return reviewService.getUserReviews(userId, targetType, false);
    }

    Long targetId = userId;

    if(userType == UserType.TRAINER) {
      targetType = TargetType.TRAINER;
    }

    if(userType == UserType.GYM) {
      targetType = TargetType.GYM;
    }

    return reviewService.getTargetReviews(targetId, targetType, false);
  }

  @GetMapping(value = "/reviews/{reviewId}")
  public Review getReview(@PathVariable Long reviewId) {
    return reviewService.getReview(reviewId);
  }

  @PatchMapping(value = "/reviews/{reviewId}")
  public void updateReview(@RequestBody Review review) {
    reviewService.setReview(review);

    ReviewUpdated reviewUpdated = new ReviewUpdated(review);

    String event = reviewUpdated.json();

    eventService.publish("review", event);
  }

}
