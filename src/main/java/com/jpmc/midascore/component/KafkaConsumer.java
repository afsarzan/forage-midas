package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-consumer")
    public void listen(Transaction transaction) {
        // You can add a print statement here to see the transactions in the console
        System.out.println("Received: " + transaction);
    }
}