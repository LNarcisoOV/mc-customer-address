package com.mastercard.customer.controller;

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

import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.CustomerDTO;
import com.mastercard.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
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

}
