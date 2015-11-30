package com.rnd.tms.data.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.ReadableDuration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.entity.BreakDetail.BreakType;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.rnd.tms.data.util.TMSTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldGroupTestApplication.class)


public class ProcessedTimingTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProcessedTimingRepository processedTimingRepository;
	
	/*@Autowired
	private BreakDetailRepository breakDetailRepository;*/
	
	@Autowired
	private TimingProfileRepository timingProfileRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	

	@Test
	//@Transactional
	public void testCreateProcessedTiming(){
		ProcessedTiming processedTiming = new ProcessedTiming();
		Employee employee = new Employee("John","Loden");
		processedTiming.setEmployee(employee);
		employee=employeeRepository.save(employee);
		
		DateTime startDateTime = new DateTime(2015,11,17,8,0,0);
		processedTiming.setInDateTime(startDateTime);
		processedTiming.setOutDateTime(startDateTime.plusHours(9));
		
		Client client = new Client("ClientCreatedForDummyTimingProfile");
		client = clientRepository.save(client);
		TimingProfile timingProfile = TMSTestUtil.getDummyTimingProfile(client);
		timingProfile = timingProfileRepository.save(timingProfile);
		processedTiming.setTimingProfile(timingProfile);
		processedTiming.setRecordCreatedDate(new Date());
		processedTiming = processedTimingRepository.save(processedTiming);
		assertNotNull(processedTiming.getId());
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plusMinutes(30);
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.TEA,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(processedTiming);
		//lunchBreakDetail = breakDetailRepository.save(lunchBreakDetail);
		breakDetails.add(lunchBreakDetail);
		
		DateTime teaBreakStart = startDateTime.plusHours(5);
		DateTime teaBreakEnd = teaBreakStart.plusMinutes(10).plusHours(1);
		BreakDetail teaBreakDetail = new BreakDetail(BreakType.TEA,teaBreakStart,teaBreakEnd);
		//teaBreakDetail.setProcessedTiming(processedTiming);
		//teaBreakDetail = breakDetailRepository.save(teaBreakDetail);
		breakDetails.add(teaBreakDetail);
		
		processedTiming.setBreakDetails(breakDetails);
		processedTiming.setRecordModifiedDate(new Date());
		processedTiming = processedTimingRepository.save(processedTiming);
		
		
		//assertNotNull(processedTiming.getBreakDetails().get(0).getId());
		
		
	}
	
	
	/*@Test
	@Transactional
	public void testBreakTime(){
		ProcessedTiming rawTiming = TMSTestUtil.createProcessedTimingWithBreaks();
		rawTiming = processedTimingRepository.save(rawTiming);
		BreakDetail breakDetail =  rawTiming.getBreakDetails().get(0);
		Duration lunchBreakDuration = breakDetail.getActualBreakDuration();
		if(lunchBreakDuration==null){
			breakDetail= breakDetailService.calculateAndUpdateBreakDetail(breakDetail, TMSTestUtil.getDummyTimingProfile());
			lunchBreakDuration = breakDetail.getActualBreakDuration();
		}
		Period lunchBreakPeriod = lunchBreakDuration.toPeriod();
		PeriodFormatter readableLunchBreakPeriod = new PeriodFormatterBuilder()
			 .printZeroAlways()
		     .minimumPrintedDigits(2)
		     .appendHours()
		     .appendSeparator(":")
		     .appendMinutes()
		     .appendSeparator(":")
		     .appendSeconds()
		     .toFormatter();
		assertNotNull(readableLunchBreakPeriod.print(lunchBreakPeriod));
	}*/
	
}
