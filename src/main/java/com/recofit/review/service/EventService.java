package com.recofit.review.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private Logger logger = LoggerFactory.getLogger(EventService.class);

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void publish(String topic, String event) {
    kafkaTemplate.send(topic, event);
    logger.info("##### Message(publish): " + event);
  }

  @KafkaListener(topics = "review", groupId = "review")
  public void subscribe(String event) {
    logger.info("##### Message(subscribe): " + event);
  }

}
