package com.recofit.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recofit.review.domain.Review;
import com.recofit.review.util.TargetType;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByUserIdAndTargetTypeAndDeleted(Long userId, TargetType targetType, boolean deleted);

  List<Review> findByTargetIdAndTargetTypeAndDeleted(Long targetId, TargetType targetType, boolean deleted);

}
