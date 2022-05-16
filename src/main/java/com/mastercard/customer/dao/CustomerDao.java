package com.mastercard.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mastercard.customer.model.Customer;


public interface CustomerDao extends JpaRepository<Customer, Long>{

}
