package com.epam.hotelreservation.reportservice.consumer;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

  private CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(topics = "${kafka.topic}", groupId = "reservation")
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    log.info("Received payload: {}", consumerRecord.value());
    latch.countDown();
  }

}

