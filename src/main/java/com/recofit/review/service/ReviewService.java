package com.recofit.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recofit.review.domain.Review;
import com.recofit.review.repository.ReviewRepository;
import com.recofit.review.util.TargetType;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  public void setReview(Review review) {
    reviewRepository.save(review);
  }

  public List<Review> getUserReviews(Long userId, TargetType targetType, boolean isDeleted) {
    List<Review> reviews = reviewRepository.findByUserIdAndTargetTypeAndIsDeleted(userId, targetType, isDeleted);

    return reviews;
  }

  public List<Review> getTargetReviews(Long targetId, TargetType targetType, boolean isDeleted) {
    List<Review> reviews = reviewRepository.findByTargetIdAndTargetTypeAndIsDeleted(targetId, targetType, isDeleted);

    return reviews;
  }

  public Review getReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).get();

    return review;
  }

}
