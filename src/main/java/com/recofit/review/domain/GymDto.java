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
public class GymDto {
  Long gymId;
  double rating;

  @Builder
  public GymDto(Long gymId, double rating) {
    this.gymId = gymId;
    this.rating = rating;
  }
}
