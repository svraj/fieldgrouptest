package com.rnd.tms.data.entity;

import java.io.Serializable;

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
	
}
