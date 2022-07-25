package com.mastercard.customer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import com.mastercard.customer.util.Constants;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name(Constants.CUSTOMER_TOPIC_NAME).partitions(5).replicas(1).build();
    }
    
    @Bean
    public NewTopic addressTopic() {
        return TopicBuilder.name(Constants.ADDRESS_TOPIC_NAME).partitions(5).replicas(1).build();
    }

}
