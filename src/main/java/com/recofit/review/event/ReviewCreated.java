package com.recofit.review.event;

import com.recofit.review.domain.Review;
import com.recofit.review.util.Event;
import com.recofit.review.util.TargetType;

import lombok.Data;

@Data
public class ReviewCreated extends Event {

  private String type;
  private Long reviewId;
  private Long targetId;
  private TargetType targetType;
  private Double ReviewRating;

  public ReviewCreated(Review review) {
    super(review);
  }

}
