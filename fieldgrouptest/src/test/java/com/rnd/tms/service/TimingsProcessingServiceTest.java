package com.rnd.tms.service;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cm.rnd.tms.business.dto.TimingsProcessDTO;
import cm.rnd.tms.business.enums.TimingRecordProcessStatus;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.RawTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.enums.BreakType;
import com.rnd.tms.data.repository.BreakDetailRepository;
import com.rnd.tms.data.repository.RawTimingRepository;
import com.rnd.tms.data.util.TMSTestUtil;
import com.rnd.tms.exceptions.TmsBusinessException;
import com.rnd.tms.services.TimingsProcessingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=FieldGroupTestApplication.class)
public class TimingsProcessingServiceTest {
	
	@Autowired
	private TimingsProcessingService timingsProcessingService;
	
	@Autowired
	private RawTimingRepository rawTimingRepository;

	@Autowired
	private BreakDetailRepository breakDetailRepository;
	
	@Test
	@Transactional
	public void testCalculateAndUpdateBreak(){
		RawTiming rawTiming = null;

		if(rawTimingRepository.findAll().size()!=0){
			rawTiming = rawTimingRepository.findAll().get(0);
		}else{
			rawTiming = TMSTestUtil.createRawTimingWithBreaks();
			rawTiming = rawTimingRepository.save(rawTiming);
		}
		
		BreakDetail breakDetail = new BreakDetail(BreakType.DINNER, new DateTime(2015,11,17,21,00), new DateTime(2015,11,17,21,50));
		breakDetail.setRawTiming(rawTiming);
		breakDetail = breakDetailRepository.save(breakDetail);
		TimingProfile timingProfile = TMSTestUtil.getDummyTimingProfile();
		try {
			breakDetail = timingsProcessingService.calculateAndUpdateBreakDetail(breakDetail, timingProfile);
		} catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(breakDetail.getActualBreakDuration(),new Duration(breakDetail.getBreakStart(),breakDetail.getBreakEnd()));
		assertEquals(timingProfile.getMinBreakDuration(), breakDetail.getEffectiveBreakDuration());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithoutTimingProfile(){
		
		RawTiming rawTiming = TMSTestUtil.createRawTimingWithSingleLunchBreakWithDuration(new Duration(25*60*1000));
		rawTimingRepository.save(rawTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processRawTiming(rawTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getRawTimingProcessResults().size());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithTimingProfile(){
		RawTiming rawTiming = TMSTestUtil.createRawTimingWithTimingProfileAndSingleLunchBreakWithDuration(new Duration(25*60*1000));
		rawTimingRepository.save(rawTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processRawTiming(rawTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(null,timingsProcessDTO.getProcessStatus());
		assertEquals(null,timingsProcessDTO.getRawTimingProcessResults());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithIncorrectTimingProfileNoDailyHours(){
		RawTiming rawTiming = TMSTestUtil.createRawTimingWithIncorrectTimingProfileNoDailyHours();
		rawTimingRepository.save(rawTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processRawTiming(rawTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getRawTimingProcessResults().size());
		assertEquals("DailyWorkingHours in TimingProfile not set",timingsProcessDTO.getRawTimingProcessResults().get(0).getRemarks());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithIncorrectTimingProfileNoBreakValues(){
		RawTiming rawTiming = TMSTestUtil.createRawTimingWithIncorrectTimingProfileNoBreakValues();
		rawTimingRepository.save(rawTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processRawTiming(rawTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getRawTimingProcessResults().size());
	}

}
