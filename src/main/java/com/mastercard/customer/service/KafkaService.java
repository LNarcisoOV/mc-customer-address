package com.mastercard.customer.service;

public interface KafkaService {
    public void sendMessage(String topicName, String payload);
}
