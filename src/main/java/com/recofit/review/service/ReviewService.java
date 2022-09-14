package com.recofit.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.recofit.review.domain.GymDto;
import com.recofit.review.domain.KafkaRatingsDto;
import com.recofit.review.domain.Review;
import com.recofit.review.domain.ReviewDTO;
// import com.recofit.review.event.ReviewCreated;
// import com.recofit.review.event.ReviewUpdated;
import com.recofit.review.repository.ReviewRepository;
import com.recofit.review.util.TargetType;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ReviewService {

  @Autowired
  private EventService eventService;
  @Autowired
  private FeignService feignService;

  @Autowired
  private ReviewRepository reviewRepository;

  public void createReview(Review review) {
    reviewRepository.save(review);

    double average = reviewRepository.averageByTargetIdAndTargetTypeAndDeleted(review.getTargetId(), review.getTargetType());

    ReviewDTO reviewDTO = ReviewDTO.builder()
      .targetId(review.getTargetId())
      .targetType(review.getTargetType())
      .reviewRating(average)
      .build();

    if(TargetType.TRAINER.equals(reviewDTO.getTargetType())) {
      String stringUserId = reviewDTO.getTargetId().toString();

      KafkaRatingsDto kafkaRatingsDto = KafkaRatingsDto.builder().userId(stringUserId).ratings(reviewDTO.getReviewRating()).build();

      eventService.sendAccount("recofit-account-ratings", kafkaRatingsDto);
    }

    if(TargetType.GYM.equals(reviewDTO.getTargetType())) {
      GymDto gymDto = GymDto.builder().gymId(reviewDTO.getTargetId()).rating(reviewDTO.getReviewRating()).build();

      eventService.sendGym("recofit-gym-ratings", gymDto);
    }

    // ReviewCreated reviewCreated = new ReviewCreated(reviewDTO);

    // String event = reviewCreated.json();

    // eventService.publish("review", event);
  }

  public List<ReviewDTO> getUserReviews(Long userId, TargetType targetType, boolean deleted) {
    List<Review> reviews = reviewRepository.findByUserIdAndTargetTypeAndDeleted(userId, targetType, deleted);

    List<ReviewDTO> reviewDTOs = reviews.stream().map(
      review -> ReviewDTO.builder()
        .reviewId(review.getReviewId())
        .reservationId(review.getReservationId())
        .userId(review.getUserId())
        .targetId(review.getTargetId())
        .userName(review.getUserName())
        .targetType(review.getTargetType())
        .targetName(review.getTargetName())
        .programType(review.getProgramType())
        .reviewRating(review.getReviewRating())
        .reviewComment(review.getReviewComment())
        .deleted(review.isDeleted())
        .build()
    ).collect(Collectors.toList());

    return reviewDTOs;
  }

  public List<ReviewDTO> getTargetReviews(Long targetId, TargetType targetType, boolean deleted) {
    List<Review> reviews = reviewRepository.findByTargetIdAndTargetTypeAndDeleted(targetId, targetType, deleted);

    List<ReviewDTO> reviewDTOs = reviews.stream().map(
      review -> ReviewDTO.builder()
        .reviewId(review.getReviewId())
        .reservationId(review.getReservationId())
        .userId(review.getUserId())
        .targetId(review.getTargetId())
        .userName(review.getUserName())
        .targetType(review.getTargetType())
        .targetName(review.getTargetName())
        .programType(review.getProgramType())
        .reviewRating(review.getReviewRating())
        .reviewComment(review.getReviewComment())
        .deleted(review.isDeleted())
        .build()
    ).collect(Collectors.toList());

    return reviewDTOs;
  }

  public ReviewDTO getReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).get();

    ReviewDTO reviewDTO = ReviewDTO.builder()
      .reviewId(review.getReviewId())
      .reservationId(review.getReservationId())
      .userId(review.getUserId())
      .targetId(review.getTargetId())
      .userName(review.getUserName())
      .targetType(review.getTargetType())
      .targetName(review.getTargetName())
      .programType(review.getProgramType())
      .reviewRating(review.getReviewRating())
      .reviewComment(review.getReviewComment())
      .deleted(review.isDeleted())
      .build();

    return reviewDTO;
  }

  public void updateReview(Review review) {
    reviewRepository.save(review);

    double average = reviewRepository.averageByTargetIdAndTargetTypeAndDeleted(review.getTargetId(), review.getTargetType());

    ReviewDTO reviewDTO = ReviewDTO.builder()
      .targetId(review.getTargetId())
      .targetType(review.getTargetType())
      .reviewRating(average)
      .build();

    if(TargetType.TRAINER.equals(reviewDTO.getTargetType())) {
      String uid = reviewDTO.getTargetId().toString();

      KafkaRatingsDto kafkaRatingsDto = KafkaRatingsDto.builder().userId(uid).ratings(reviewDTO.getReviewRating()).build();

      eventService.sendAccount("recofit-account-ratings", kafkaRatingsDto);
    }

    if(TargetType.GYM.equals(reviewDTO.getTargetType())) {
      GymDto gymDto = GymDto.builder().gymId(reviewDTO.getTargetId()).rating(reviewDTO.getReviewRating()).build();

      eventService.sendGym("recofit-gym-ratings", gymDto);
    }

    // ReviewUpdated reviewUpdated = new ReviewUpdated(reviewDTO);

    // String event = reviewUpdated.json();

    // eventService.publish("review", event);
  }

  @CircuitBreaker(name = "review", fallbackMethod = "fallback")
  public ResponseEntity<String> circuitBreaker() {
    return feignService.accountTest();
  }

  private ResponseEntity<String> fallback(CallNotPermittedException e) {
    return ResponseEntity.ok("Handled the exception when the CircuitBreaker is open");
  }

}
