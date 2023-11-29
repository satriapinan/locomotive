package com.locomotive.infoscheduler.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "loco", groupId = "locomotive-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}
