package com.recofit.review.event;

import com.recofit.review.domain.ReviewDTO;
import com.recofit.review.util.Event;
import com.recofit.review.util.TargetType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdated extends Event {

  private String type;
  private Long targetId;
  private TargetType targetType;
  private Double ReviewRating;

  public ReviewUpdated(ReviewDTO reviewDTO) {
    super(reviewDTO);
  }

}
