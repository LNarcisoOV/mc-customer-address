package com.mastercard.customer.service;

import java.util.Optional;

import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.CustomerDTO;

public interface CustomerService {
	Optional<Customer> getById(Long id);

	Optional<Customer> save(CustomerDTO customerDTO);
}
