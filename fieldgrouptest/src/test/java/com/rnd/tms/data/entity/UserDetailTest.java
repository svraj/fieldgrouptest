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
import com.rnd.tms.data.entity.UserDetail;
import com.rnd.tms.data.enums.UserType;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.data.repository.UserDetailRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldGroupTestApplication.class)
public class UserDetailTest {
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Test
	@Transactional
	public void testCreateUser(){
		
		UserDetail user = new UserDetail();
		String username = "sajanvraj";
		String email = "sajanvraj@test.com";
		user.setUserType(UserType.EMPLOYEE);
		user.setUsername(username);
		user.setEmailId(email);
		user = userDetailRepository.save(user);
		
		assertNotEquals(userDetailRepository.findAll().size(),0);
		
	}
	
	

}
