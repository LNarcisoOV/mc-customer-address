package com.mastercard.customer.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.mastercard.customer.client.ZipValidatorClient;
import com.mastercard.customer.dao.AddressDao;
import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.AddressDTO;
import com.mastercard.customer.util.Constants;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ZipValidatorClient zipValidatorClient;
    
    @Autowired
    private KafkaService kafkaService;
    
    @Override
    public List<Address> getById(Long customerId) {
        Optional<Customer> customerOpt = customerService.getById(customerId);
        return customerOpt.isPresent() ? addressDao.findBy(customerOpt.get())
                : Collections.emptyList();
    }

    @Override
    public Optional<Address> save(Long customerId, AddressDTO addressDTO) {
        Optional<Customer> customerOpt = customerService.getById(customerId);

        if (customerOpt.isPresent()) {
            Address address = modelMapper.map(addressDTO, Address.class);

            Boolean isZipCodeValid = zipValidatorClient.validateZipCode(address.getZipCode());

            if (isZipCodeValid) {
                kafkaService.sendMessage(Constants.ADDRESS_TOPIC_NAME, new Gson().toJson(address));
                address.setCustomer(customerOpt.get());
                Address addressDB = addressDao.save(address);
                return Optional.of(addressDB);
            } else {
                throw new RuntimeException("Invalid zip code.");
            }
        } else {
            return Optional.empty();
        }
    }
    
    @Override
    public String getZipValidatorPort() {
        return zipValidatorClient.getPort();
    }

}
