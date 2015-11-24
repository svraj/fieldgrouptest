package com.rnd.tms.services;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService{

	@Override
	public String getTestMessage() {
		// TODO Auto-generated method stub
		return "This is a test message!!!!";
				
	}

}
