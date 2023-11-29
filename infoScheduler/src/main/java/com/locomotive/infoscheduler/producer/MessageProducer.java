package com.locomotive.infoscheduler.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "loco";
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    private final String[] locomotiveCodes = {"L001", "L002", "L003", "L004", "L005", "L006", "L007", "L008", "L009", "L010"};
    private final String[] locomotiveNames = {"Loco 1", "Loco 2", "Loco 3", "Loco 4", "Loco 5", "Loco 6", "Loco 7", "Loco 8", "Loco 9", "Loco 10"};
    private final String[] dimensions = {"10x5x3", "8x4x2", "12x6x4", "9x3x5", "11x7x6", "15x9x8", "13x6x3", "7x5x2", "14x8x7", "16x10x9"};
    private final String[] statuses = {"Active", "Inactive", "Under Maintenance"};

    @Scheduled(fixedRate = 10000)
    public void sendMessage() {
        Random random = new Random();
        int index = random.nextInt(locomotiveCodes.length);

        String code = locomotiveCodes[index];
        String name = locomotiveNames[index];
        String dimension = dimensions[index];
        String status = statuses[random.nextInt(statuses.length)];
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String message = "Locomotive Info - Code: " + code +
                ", Name: " + name +
                ", Dimension: " + dimension +
                ", Status: " + status +
                ", Date and Time: " + dateTime;

        kafkaTemplate.send(TOPIC, message).whenComplete(
                (stringStringSendResult, throwable) -> log.info("Message result: {}", stringStringSendResult)
        );
    }
}
