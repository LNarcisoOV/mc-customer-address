package com.mastercard.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class McCustomerAddressApplication {

	public static void main(String[] args) {
		SpringApplication.run(McCustomerAddressApplication.class, args);
	}

}
