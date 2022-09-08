package com.recofit.review.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recofit.review.domain.Review;
import com.recofit.review.domain.ReviewDTO;
import com.recofit.review.service.ReviewService;
import com.recofit.review.util.Response;
import com.recofit.review.util.TargetType;
import com.recofit.review.util.UserType;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @PostMapping(value = "/reviews")
  public ResponseEntity<Response> createReview(@RequestBody Review review) {
    reviewService.createReview(review);

    Response response = Response.builder()
      .result("SUCCESS")
      .build();

    return new ResponseEntity<Response>(response, HttpStatus.OK);
  }

  @GetMapping(value = "/reviews")
  public ResponseEntity<Response> getReviews(@RequestParam(required = false, value = "target-type") TargetType targetType) {
    Long userId = 1L;
    UserType userType = UserType.USER;
    List<ReviewDTO> reviewDTOs = Collections.emptyList();

    if(userType == UserType.USER) {
      reviewDTOs = reviewService.getUserReviews(userId, targetType, false);
    }

    Long targetId = userId;

    if(userType == UserType.TRAINER) {
      targetType = TargetType.TRAINER;
    }

    if(userType == UserType.GYM) {
      targetType = TargetType.GYM;
    }

    reviewDTOs = reviewService.getTargetReviews(targetId, targetType, false);

    Response response = Response.builder()
      .result("SUCCESS")
      .data(reviewDTOs)
      .build();

    return new ResponseEntity<Response>(response, HttpStatus.OK);
  }

  @GetMapping(value = "/reviews/{reviewId}")
  public ResponseEntity<Response> getReview(@PathVariable Long reviewId) {
    ReviewDTO reviewDTO = reviewService.getReview(reviewId);

    Response response = Response.builder()
      .result("SUCCESS")
      .data(reviewDTO)
      .build();

    return new ResponseEntity<Response>(response, HttpStatus.OK);
  }

  @PatchMapping(value = "/reviews/{reviewId}")
  public ResponseEntity<Response> updateReview(@RequestBody Review review) {
    reviewService.updateReview(review);

    Response response = Response.builder()
      .result("SUCCESS")
      .build();

    return new ResponseEntity<Response>(response, HttpStatus.OK);
  }

}
