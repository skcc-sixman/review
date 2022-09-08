package com.recofit.review.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

@Setter
public class Event {

  private Logger logger = LoggerFactory.getLogger(Event.class);

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
    logger.info("##### Event(json): " + str);
    return str;
  }

}
