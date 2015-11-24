package com.rnd.tms.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Client extends BaseEntity{

	private String companyName;
	private String contactNumber;

	@OneToOne(cascade=CascadeType.ALL)
	private Address address;
	
	public Client(String companyName) {
		super();
		this.companyName = companyName;
		this.address= new Address();
	}
	
	public Client(){}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
}
