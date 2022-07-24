package com.recofit.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recofit.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
