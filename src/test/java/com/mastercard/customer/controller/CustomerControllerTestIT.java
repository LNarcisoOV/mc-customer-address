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

import com.mastercard.customer.service.CustomerService;
import com.mastercard.customer.model.Customer;
import com.mastercard.customer.model.dto.CustomerDTO;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
