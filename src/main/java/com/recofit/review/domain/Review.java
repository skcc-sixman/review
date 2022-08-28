package com.recofit.review.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recofit.review.event.ReviewCreated;
import com.recofit.review.util.AuditingDate;
import com.recofit.review.util.TargetType;

import com.recofit.review.util.AuditingDate;
import com.recofit.review.util.TargetType;

import lombok.Getter;

@Entity
@Getter
public class Review extends AuditingDate {

  @Id
  @GeneratedValue
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

  private boolean isDeleted;

}
