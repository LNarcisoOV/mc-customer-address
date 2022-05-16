package com.mastercard.customer.model.dto;

import java.util.List;

import com.mastercard.customer.model.Address;

public class CustomerDTO {

	private String name;
	private String lastName;
	private List<Address> addresses;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	
}
