package com.mastercard.customer.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mastercard.customer.dao.CustomerDao;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.CustomerDTO;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;	
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Optional<Customer> getById(Long id) {
		return customerDao.findById(id);
	}
	
	@Override
	public Optional<Customer> save(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		Customer customerDB = customerDao.save(customer);
		return Optional.of(customerDB);
	}
}
