package com.recofit.review.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Review {

  @Id
  @GeneratedValue
  private Long reviewId;

  private Long reservationId;
  private Long userId;
  private Long targetId;

  private String targetType;

  private double reviewRating;
  private String reviewComment;

}
