package com.recofit.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KafkaRatingsDto {
  String userId;
  double ratings;

  @Builder
  public KafkaRatingsDto(String userId, double ratings) {
    this.userId = userId;
    this.ratings = ratings;
  }
}
