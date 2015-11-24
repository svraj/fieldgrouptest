package com.rnd.tms.data.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.repository.ProcessedTimingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=FieldGroupTestApplication.class)
public class ProcessedTimingTest {
	
	@Autowired
	private ProcessedTimingRepository processedTimingRepository;
	
	/*@Autowired
	private ProcessedTimingRepository processedTimingRepository;*/
	
	@Test
	public void testProcessedTimingCreate(){
		ProcessedTiming processedTiming = new ProcessedTiming();
		
	}

}
