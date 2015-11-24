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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.enums.BreakType;
import com.rnd.tms.data.repository.BreakDetailRepository;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.data.repository.RawTimingRepository;
import com.rnd.tms.data.util.TMSTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldGroupTestApplication.class)


public class RawTimingTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RawTimingRepository rawTimingRepository;
	
	@Autowired
	public BreakDetailRepository breakDetailRepository;
	

	@Test
	//@Transactional
	public void testCreateRawTiming(){
		RawTiming rawTiming = new RawTiming();
		Employee employee = new Employee("John","Loden");
		rawTiming.setEmployee(employee);
		
		DateTime startDateTime = new DateTime(2015,11,17,8,0,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(9));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plusMinutes(30);
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.TEA,lunchBreakStart,lunchBreakEnd);
		lunchBreakDetail.setRawTiming(rawTiming);
		
		breakDetails.add(lunchBreakDetail);
		
		DateTime teaBreakStart = startDateTime.plusHours(5);
		DateTime teaBreakEnd = teaBreakStart.plusMinutes(10).plusHours(1);
		BreakDetail teaBreakDetail = new BreakDetail(BreakType.TEA,teaBreakStart,teaBreakEnd);
		teaBreakDetail.setRawTiming(rawTiming);
		breakDetails.add(teaBreakDetail);
		rawTiming.setBreakDetails(breakDetails);
		
		rawTiming.setRecordCreatedDate(new Date());
		rawTiming.setTimingProfile(TMSTestUtil.getDummyTimingProfile());
		//rawTiming.setRecordModifiedDate(new Date());
		
		RawTiming savedRawTiming = rawTimingRepository.save(rawTiming);
		assertNotNull(savedRawTiming.getId());
		
	}
	
	
	/*@Test
	@Transactional
	public void testBreakTime(){
		RawTiming rawTiming = TMSTestUtil.createRawTimingWithBreaks();
		rawTiming = rawTimingRepository.save(rawTiming);
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
