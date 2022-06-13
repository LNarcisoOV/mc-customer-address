package com.mastercard.customer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.AddressDTO;
import com.mastercard.customer.model.dto.CustomerDTO;
import com.mastercard.customer.service.AddressService;
import com.mastercard.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
		Optional<Customer> customerOpt = customerService.getById(id);
		if(customerOpt.isPresent()) {
			CustomerDTO customerDTO = modelMapper.map(customerOpt.get(), CustomerDTO.class);
			return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK); 
		} else {
			return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_FOUND); 
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO) {
		try {
			Optional<Customer> customerOpt = customerService.save(customerDTO);
			if(customerOpt.isPresent()) {
				CustomerDTO customerDTOResponse = modelMapper.map(customerOpt.get(), CustomerDTO.class);
				return new ResponseEntity<CustomerDTO>(customerDTOResponse, HttpStatus.CREATED); 
			} else {
				return new ResponseEntity<CustomerDTO>(HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		} catch(RuntimeException runtimeException) {
			throw new RuntimeException("An exception occurs;");
		}
	}
	
	@GetMapping("/{customerId}/address")
	public ResponseEntity<List<AddressDTO>> getAddressById(@PathVariable Long customerId) {
		List<Address> adresses = addressService.getById(customerId);
		if(!adresses.isEmpty()) {
			List<AddressDTO> addressesDTO = Arrays.asList(modelMapper.map(adresses, AddressDTO[].class));
			return new ResponseEntity<List<AddressDTO>>(addressesDTO, HttpStatus.OK); 
		} else {
			return new ResponseEntity<List<AddressDTO>>(HttpStatus.NOT_FOUND); 
		}
	}
	
	@PostMapping("/{customerId}/address")
	public ResponseEntity<AddressDTO> saveAddress(@PathVariable Long customerId, @RequestBody AddressDTO adressDTO) {
		try {
			Optional<Address> addressOpt = addressService.save(customerId, adressDTO);
			if(addressOpt.isPresent()) {
				AddressDTO addressDTOResponse = modelMapper.map(addressOpt.get(), AddressDTO.class);
				return new ResponseEntity<AddressDTO>(addressDTOResponse, HttpStatus.CREATED); 
			} else {
				return new ResponseEntity<AddressDTO>(HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		} catch(RuntimeException runtimeException) {
			throw new RuntimeException(runtimeException.getMessage());
		}
	}

}
