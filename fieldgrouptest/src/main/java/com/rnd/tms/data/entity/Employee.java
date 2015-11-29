package com.rnd.tms.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@SuppressWarnings("serial")
@Entity
public class Employee extends BaseEntity implements Serializable{

	private String firstName;
	private String lastName;
	private String employeeCode;
	private Date dateOfBirth;
	private Boolean foreigner;
	private String email;
	private String phoneNumber;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Address address;
	

	public Employee(String firstName, String lastName) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.address=new Address();
	}
	
	public Employee() {
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getForeigner() {
		return foreigner;
	}

	public void setForeigner(Boolean foreigner) {
		this.foreigner = foreigner;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
