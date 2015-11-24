package com.rnd.tms.data.entity;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.entity.Address;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.repository.EmployeeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldGroupTestApplication.class)
public class EmployeeTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	@Transactional
	public void testCreateEmployee(){
		
		Employee employee = new Employee();
		String firstName = "Sajan";
		String lastName = "Raj";
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		Address address = new Address("10","2-6 Campbell Street","Parramatta",2150,"NSW","Australia");
		//address.setEmployee(employee);
		employee.setAddress(address);
		employee = employeeRepository.save(employee);
		assertNotEquals(employeeRepository.findByLastNameStartsWithIgnoreCase(lastName).size(),0);
		
	}
	
	

}
