package com.mastercard.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topicName, String payload) {
        try {
            kafkaTemplate.send(topicName, payload);
        } catch (RuntimeException re) {
            throw new RuntimeException(re);
        }
    }

}
