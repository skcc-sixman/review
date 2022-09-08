package com.recofit.review.domain;

import com.recofit.review.util.TargetType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {

  private Long reviewId;

  private Long reservationId;
  private Long userId;
  private Long targetId;

  private String userName;
  private TargetType targetType;
  private String targetName;
  private Integer programType;

  private double reviewRating;
  private String reviewComment;

  private boolean deleted;

}
