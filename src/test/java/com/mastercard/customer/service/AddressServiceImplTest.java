package com.mastercard.customer.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import com.mastercard.customer.client.ZipValidatorClient;
import com.mastercard.customer.dao.AddressDao;
import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.AddressDTO;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @Mock
    private AddressDao addressDao;

    @Mock
    private ZipValidatorClient zipValidatorClient;

    @Mock
    private CustomerService customerService;
    
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @Test
    public void testSaveAddressToExistingCustomer() {
        given(addressDao.save(any())).willReturn(address1());

        Address address = addressDao.save(any());
        assertEquals(Long.valueOf(address.getAddressId()), Long.valueOf(1));
        assertEquals(address.getState(), "NY");
        assertEquals(address.getStreet(), "Street 1");
        assertEquals(address.getZipCode(), "83592-135");
    }
    
    @Test 
    public void testSaveAddressToNonExistingCustomer() {
        given(customerService.getById(1L)).willReturn(Optional.empty());
        given(zipValidatorClient.validateZipCode(anyString())).willReturn(Boolean.TRUE);
        given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(address1());
        
        Optional<Address> optAddress = addressServiceImpl.save(1L, any(AddressDTO.class));
        assertTrue(optAddress.isEmpty());
    }

    @Test 
    public void testSaveAddressWithInvalidZip() {
        try {
            given(customerService.getById(1L)).willReturn(customer1());
            given(zipValidatorClient.validateZipCode(anyString())).willReturn(Boolean.FALSE);
            given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(address1());
            
            addressServiceImpl.save(1L, any(AddressDTO.class));
        } catch (RuntimeException runtimeException) {
            assertEquals(runtimeException.getMessage(), "Invalid zip code.");
        }
    }

    private Optional<Customer> customer1() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        return Optional.of(customer);
    }

    private Address address1() {
        Address address = new Address();

        address.setAddressId(1L);
        address.setCustomer(customer1().get());
        address.setState("NY");
        address.setStreet("Street 1");
        address.setZipCode("83592-135");

        return address;
    }

}
