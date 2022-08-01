package com.recofit.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recofit.review.domain.Review;
import com.recofit.review.repository.ReviewRepository;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  public void createReview(Review review) {
    reviewRepository.save(review);
  }

  public List<Review> getReviews() {
    List<Review> reviews = reviewRepository.findAll();

    return reviews;
  }

  public Review getReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).get();

    return review;
  }

}
