package com.mastercard.customer.client;

import feign.Param;
import feign.RequestLine;

public interface ZipValidatorClient {
    
    @RequestLine("GET /{zipCode}")
    Boolean validateZipCode(@Param("zipCode") String zipCode);

}
