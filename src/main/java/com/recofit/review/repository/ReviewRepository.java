package com.recofit.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.recofit.review.domain.Review;
import com.recofit.review.util.TargetType;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByUserIdAndTargetTypeAndDeleted(Long userId, TargetType targetType, boolean deleted);

  List<Review> findByTargetIdAndTargetTypeAndDeleted(Long targetId, TargetType targetType, boolean deleted);

  int countByUserIdAndDeleted(Long userId, boolean deleted);

  int countByTargetIdAndTargetTypeAndDeleted(Long targetId, TargetType targetType, boolean deleted);

  @Query("select avg(e.reviewRating) from Review e where e.targetId = :targetId and targetType = :targetType and deleted = false")
  double averageByTargetIdAndTargetTypeAndDeleted(@Param("targetId") Long targetId, @Param("targetType") TargetType targetType);

}
