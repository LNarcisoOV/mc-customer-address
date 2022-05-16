package com.mastercard.customer.model.dto;

import java.util.Objects;

public class AddressDTO {

	private String type;

	private String street;

	private String city;

	private String state;

	private String zipCode;

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
		return Objects.hash(city, state, street, type, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDTO other = (AddressDTO) obj;
		return Objects.equals(city, other.city) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(type, other.type)
				&& Objects.equals(zipCode, other.zipCode);
	}

	@Override
	public String toString() {
		return "AddressDTO [type=" + type + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + "]";
	}
}
