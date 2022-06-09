package com.mastercard.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mastercard.customer.client.ZipValidatorClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

@Configuration
public class ZipValidatorFeignConfig {
    
    @Bean
    public ZipValidatorClient vipValidatorClient(){
        return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new GsonEncoder())
        .decoder(new GsonDecoder())
        .logger(new Slf4jLogger(ZipValidatorClient.class))
        .target(ZipValidatorClient.class, "http://localhost:8081/zip");
    }

}
