package com.rnd.tms.data.entity;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Address extends BaseEntity{
	
	private String unitNumber;
	private String streetNoName;
	private String suburb;
	private Integer postCode;
	private String state;
	private String country;
	
	public Address(String unitNumber, String streetNoName, String suburb, Integer postCode, String state,
			String country) {
		this.unitNumber=unitNumber;
		this.streetNoName=streetNoName;
		this.suburb=suburb;
		this.state=state;
		this.postCode=postCode;
		this.country=country;
	}
	
	public Address(){
		
	}
	
	public String getStreetNoName() {
		return streetNoName;
	}
	public void setStreetNoName(String streetNoName) {
		this.streetNoName = streetNoName;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public Integer getPostCode() {
		return postCode;
	}
	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

}
