package com.recofit.review.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {

  private String result;
  private Object data;

}
