package com.recofit.review.util;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

@Setter
public class Event {

  private String type;

  public Event() {
    this.setType(this.getClass().getSimpleName());
  }

  public Event(Object entity) {
    this();

    BeanUtils.copyProperties(entity, this);
  }

  public String json() {
    ObjectMapper objectMapper = new ObjectMapper();

    String str = "";

    try {
      str = objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return str;
  }

}
