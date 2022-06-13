package com.mastercard.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MC-ZIP-CODE-VALIDATOR")
public interface ZipValidatorClient {
    
    @GetMapping("/zip/{zipCode}")
    Boolean validateZipCode(@PathVariable("zipCode") String zipCode);
    
    @GetMapping("/zip/port")
    String getPort();

}
