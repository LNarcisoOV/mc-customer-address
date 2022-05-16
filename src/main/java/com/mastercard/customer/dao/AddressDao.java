package com.mastercard.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mastercard.customer.model.Address;
import com.mastercard.customer.model.Customer;

public interface AddressDao extends JpaRepository<Address, Long>{

	@Query("SELECT a FROM Address a WHERE a.customer = ?1 ")
    List<Address> findBy(Customer customer);
}
