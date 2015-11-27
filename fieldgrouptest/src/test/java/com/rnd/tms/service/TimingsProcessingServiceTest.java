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

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.business.dto.TimingsProcessDTO;
import com.rnd.tms.business.enums.TimingRecordProcessStatus;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.enums.BreakType;
import com.rnd.tms.data.repository.BreakDetailRepository;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.data.util.TMSTestUtil;
import com.rnd.tms.exceptions.TmsBusinessException;
import com.rnd.tms.services.TimingsProcessingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=FieldGroupTestApplication.class)
public class TimingsProcessingServiceTest {
	
	@Autowired
	private TimingsProcessingService timingsProcessingService;
	
	@Autowired
	private ProcessedTimingRepository processedTimingRepository;

	@Autowired
	private BreakDetailRepository breakDetailRepository;
	
	@Autowired 
	private ClientRepository clientRepository;
	
	@Test
	@Transactional
	public void testCalculateAndUpdateBreak(){
		ProcessedTiming processedTiming = null;

		if(processedTimingRepository.findAll().size()!=0){
			processedTiming = processedTimingRepository.findAll().get(0);
		}else{
			processedTiming = TMSTestUtil.createProcessedTimingWithBreaks();
			processedTiming = processedTimingRepository.save(processedTiming);
		}
		
		BreakDetail breakDetail = new BreakDetail(BreakType.DINNER, new DateTime(2015,11,17,21,00), new DateTime(2015,11,17,21,50));
		breakDetail.setProcessedTiming(processedTiming);
		breakDetail = breakDetailRepository.save(breakDetail);
		Client client = new Client("ClientCreatedForDummyTimingProfile");
		client = clientRepository.save(client);
		TimingProfile timingProfile = TMSTestUtil.getDummyTimingProfile(client);
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
		
		ProcessedTiming processedTiming = TMSTestUtil.createProcessedTimingWithSingleLunchBreakWithDuration(new Duration(25*60*1000));
		processedTimingRepository.save(processedTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processProcessedTiming(processedTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getProcessedTimingProcessResults().size());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithTimingProfile(){
		Client client = new Client("ClientCreatedForDummyTimingProfile");
		client = clientRepository.save(client);
		ProcessedTiming processedTiming = TMSTestUtil.createProcessedTimingWithTimingProfileAndSingleLunchBreakWithDuration(client,new Duration(25*60*1000));
		processedTimingRepository.save(processedTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processProcessedTiming(processedTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(null,timingsProcessDTO.getProcessStatus());
		assertEquals(null,timingsProcessDTO.getProcessedTimingProcessResults());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithIncorrectTimingProfileNoDailyHours(){
		ProcessedTiming processedTiming = TMSTestUtil.createProcessedTimingWithIncorrectTimingProfileNoDailyHours();
		processedTimingRepository.save(processedTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processProcessedTiming(processedTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getProcessedTimingProcessResults().size());
		assertEquals("DailyWorkingHours in TimingProfile not set",timingsProcessDTO.getProcessedTimingProcessResults().get(0).getRemarks());
	}
	
	@Test
	@Transactional
	public void testProcessedTimingCalculationWithIncorrectTimingProfileNoBreakValues(){
		ProcessedTiming processedTiming = TMSTestUtil.createProcessedTimingWithIncorrectTimingProfileNoBreakValues();
		processedTimingRepository.save(processedTiming);
		
		TimingsProcessDTO timingsProcessDTO = null;
		try{
			timingsProcessDTO = timingsProcessingService.processProcessedTiming(processedTiming);
		}catch (TmsBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(timingsProcessDTO);
		assertEquals(timingsProcessDTO.getProcessStatus(),TimingRecordProcessStatus.FAILED);
		assertEquals(1,timingsProcessDTO.getProcessedTimingProcessResults().size());
	}

}
