package com.recofit.review.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="test", url = "http://account-service:8086/api/accounts")
public interface FeignService {

  @GetMapping("/test/1")
  public ResponseEntity<String> accountTest();

}
