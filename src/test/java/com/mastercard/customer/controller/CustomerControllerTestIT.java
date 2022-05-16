package com.mastercard.customer.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mastercard.customer.service.AddressService;
import com.mastercard.customer.service.CustomerService;
import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.AddressDTO;
import com.mastercard.customer.model.dto.CustomerDTO;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ModelMapper modelMapper;
    
    @MockBean
    private CustomerService customerService;
    
    @MockBean
    private AddressService addressService;

    @Test
    public void findCustomerById() throws Exception {
        given(customerService.getById(Mockito.anyLong())).willReturn(customer1());
        given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(customer1DTO());

        mockMvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("John")))
                .andExpect(jsonPath("lastName", is("Doe")));
    }
    
    @Test
    public void findByIdUsingInvalidId() throws Exception {
    	 given(customerService.getById(3L)).willReturn(Optional.empty());

         mockMvc.perform(get("/customer/3")
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());
    }
    
    @Test
    public void saveCustomer() throws Exception {
        given(customerService.save(Mockito.any())).willReturn(customer1());
        given(modelMapper.map(Mockito.any(), Mockito.any())).willReturn(customer1DTO());

        mockMvc.perform(post("/customer/")
                .contentType(MediaType.APPLICATION_JSON)
        		.content(customer1DTOJSon()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is("John")))
                .andExpect(jsonPath("lastName", is("Doe")));
    }
    
    @Test
    public void saveCustomerInternalServerError() throws Exception {
        given(customerService.save(Mockito.any())).willReturn(Optional.empty());

        mockMvc.perform(post("/customer/")
                .contentType(MediaType.APPLICATION_JSON)
        		.content(customer1DTOJSon()))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void findAddresByInvalidCustomerId() throws Exception {
        given(addressService.getById(Mockito.anyLong())).willReturn(new ArrayList<Address>());

        mockMvc.perform(get("/customer/1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(address1DTOJSon()))
                .andExpect(status().isNotFound());
    }
    
    private Optional<Customer> customer1() {
    	Customer customer = new Customer();
    	customer.setName("John");
    	customer.setLastName("Doe");
    	return Optional.of(customer);
    }
    
    private CustomerDTO customer1DTO() {
    	CustomerDTO customer = new CustomerDTO();
    	customer.setName("John");
    	customer.setLastName("Doe");
    	return customer;
    }

    private String customer1DTOJSon() {
    	return "{" + "\"name\"" + " : " +  "\"John\"" + ", "
    			+ "\"lastName\"" + " : " +  "\"Doe\"" + "}";
    }
    
    private List<Address> addresses1() {
    	Address address1 = new Address();
    	address1.setType("BILLING");
    	address1.setCity("Albuquerque");
    	address1.setState("New Mexico");
    	address1.setStreet("Negro Arroios");
    	address1.setCustomer(customer1().get());
    	
    	List<Address> addresses = new ArrayList<>();
    	addresses.add(address1);
    	
    	return addresses;
    }
    
    private List<AddressDTO> addresses1DTO() {
    	AddressDTO address1 = new AddressDTO();
    	address1.setType("BILLING");
    	address1.setCity("Albuquerque");
    	address1.setState("New Mexico");
    	address1.setStreet("Negro Arroios");
    	
    	List<AddressDTO> addresses = new ArrayList<>();
    	addresses.add(address1);
    	
    	return addresses;
    }
    
    private String address1DTOJSon() {
    	return "{" + "\"type\"" + " : " +  "\"BILLING\"" + ", "
    			+ "\"city\"" + " : " +  "\"Albuquerque\"" 
    			+ "\"state\"" + " : " +  "\"New Mexico\"" 
    			+ "\"street\"" + " : " +  "\"Negro Arroios\"" +
    			"}";
    }
    
    private List<Address> addresses2() {
    	Address address1 = new Address();
    	address1.setType("BILLING");
    	address1.setCity("Albuquerque");
    	address1.setState("New Mexico");
    	address1.setStreet("Negro Arroios");
    	address1.setCustomer(customer1().get());
    	
    	Address address2 = new Address();
    	address2.setType("SHIPPING");
    	address2.setCity("Boston");
    	address2.setState("Massachusetts");
    	address2.setStreet("last street");
    	address2.setCustomer(customer1().get());
    	
    	List<Address> addresses = new ArrayList<>();
    	addresses.add(address1);
    	addresses.add(address2);
    	return addresses;
    }
    
    private List<AddressDTO> addresses2DTO() {
    	AddressDTO address1 = new AddressDTO();
    	address1.setType("BILLING");
    	address1.setCity("Albuquerque");
    	address1.setState("New Mexico");
    	address1.setStreet("Negro Arroios");
    	
    	AddressDTO address2 = new AddressDTO();
    	address2.setType("SHIPPING");
    	address2.setCity("Boston");
    	address2.setState("Massachusetts");
    	address2.setStreet("last street");
    	
    	List<AddressDTO> addresses = new ArrayList<>();
    	addresses.add(address1);
    	addresses.add(address2);
    	return addresses;
    }
    
    private String address2DTOJSon() {
    	return "[{" + "\"type\"" + " : " +  "\"BILLING\"" + ", "
    			+ "\"city\"" + " : " +  "\"Albuquerque\"" 
    			+ "\"state\"" + " : " +  "\"New Mexico\"" 
    			+ "\"street\"" + " : " +  "\"Negro Arroios\"" +
    			"},"
    			+
    			"{" + "\"type\"" + " : " +  "\"SHIPPING\"" + ", "
    			+ "\"city\"" + " : " +  "\"Boston\"" 
    			+ "\"state\"" + " : " +  "\"Massachusetts\"" 
    			+ "\"street\"" + " : " +  "\"last street\"" +
    			"}]";
    }
    
}
