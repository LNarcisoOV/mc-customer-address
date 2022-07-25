package com.mastercard.customer.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.mastercard.customer.dao.CustomerDao;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.CustomerDTO;
import com.mastercard.customer.util.Constants;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private KafkaService kafkaService;

	@Override
	public Optional<Customer> getById(Long id) {
		return customerDao.findById(id);
	}
	
	@Override
	public Optional<Customer> save(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		Customer customerDB = customerDao.save(customer);
		kafkaService.sendMessage(Constants.CUSTOMER_TOPIC_NAME, new Gson().toJson(customerDB));
		return Optional.of(customerDB);
	}
}
