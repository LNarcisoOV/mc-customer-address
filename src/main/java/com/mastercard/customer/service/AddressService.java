package com.mastercard.customer.service;

import java.util.List;
import java.util.Optional;

import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.dto.AddressDTO;

public interface AddressService {
	List<Address> getById(Long customerId);

	Optional<Address> save(Long customerId, AddressDTO addressDTO);
}
