package com.mastercard.customer.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id", nullable=false)
	private Customer customer;
	
	@Column
	private int type;
	
	@Column
	private String street;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private String zipCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(city, customer, id, state, street, type, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(customer, other.customer)
				&& Objects.equals(id, other.id) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && type == other.type && Objects.equals(zipCode, other.zipCode);
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", customer=" + customer + ", type=" + type + ", street=" + street + ", city="
				+ city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
	
	
}
