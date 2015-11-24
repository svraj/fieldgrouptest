package com.rnd.tms.services;


import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.rnd.tms.business.dto.RawTimingProcessResult;
import cm.rnd.tms.business.dto.TimingsProcessDTO;
import cm.rnd.tms.business.enums.TimingRecordProcessStatus;

import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.RawTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.enums.BreakType;
import com.rnd.tms.data.repository.BreakDetailRepository;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.exceptions.TmsBusinessException;
import com.rnd.tms.util.TMSUtils;

@Service("timingsProcessingService")
public class TimingsProcessingServiceImpl implements TimingsProcessingService{
	
	@Autowired
	private ProcessedTimingRepository processedTimingRepository;
	
	@Autowired
	private BreakDetailRepository breakDetailRepository;

	public TimingsProcessDTO processRawTiming(RawTiming rawTiming){
		TimingsProcessDTO timingsProcessDTO = new TimingsProcessDTO();
		List<RawTimingProcessResult> rawTimingProcessResults =  null;
		RawTimingProcessResult rawTimingProcessResult = null;
		
		ProcessedTiming processedTiming =  new ProcessedTiming(rawTiming);
		
		if(rawTiming.getInDateTime()!=null){
			processedTiming.setInDate(rawTiming.getInDateTime().toLocalDate());
			processedTiming.setInTime(rawTiming.getInDateTime().toLocalTime());
		}
		if(rawTiming.getOutDateTime()!=null){
			processedTiming.setOutDate(rawTiming.getOutDateTime().toLocalDate());
			processedTiming.setOutTime(rawTiming.getOutDateTime().toLocalTime());
		}
		
		try {
			setBreakCalculations(rawTiming,processedTiming);
			setHourCalculations(rawTiming,processedTiming);
			setIdealTimeOut(rawTiming, processedTiming);
			setOvertimeCalculations(rawTiming,processedTiming);
			setDayBalance(rawTiming,processedTiming);
			/*timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.SUCCESS);
			rawTimingProcessResult.setProcessStatus(TimingRecordProcessStatus.SUCCESS);*/
			processedTimingRepository.save(processedTiming);
		} catch (TmsBusinessException e) {
			rawTimingProcessResult = getProcessResultSkeltonForRawTiming(rawTiming);
			timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.FAILED);
			rawTimingProcessResult.setProcessStatus(TimingRecordProcessStatus.FAILED);
			//	TODO Set proper message below
			rawTimingProcessResult.setRemarks(e.getMessage());
			e.printStackTrace();
			rawTimingProcessResults =  new ArrayList<RawTimingProcessResult>();
			rawTimingProcessResults.add(rawTimingProcessResult);
		}catch(Exception e){
			rawTimingProcessResult = getProcessResultSkeltonForRawTiming(rawTiming);
			timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.FAILED);
			rawTimingProcessResult.setProcessStatus(TimingRecordProcessStatus.FAILED);
			rawTimingProcessResult.setRemarks(e.getMessage());
			e.printStackTrace();
			rawTimingProcessResults =  new ArrayList<RawTimingProcessResult>();
			rawTimingProcessResults.add(rawTimingProcessResult);
		}
		
		
		
		timingsProcessDTO.setRawTimingProcessResults(rawTimingProcessResults);
		return timingsProcessDTO;
	}
	
	private void setDayBalance(RawTiming rawTiming,ProcessedTiming processedTiming) throws TmsBusinessException {
		
		if(rawTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		
		if(rawTiming.getTimingProfile().getDailyWorkHours()==null){
			throw new TmsBusinessException("DailyWorkingHours in TimingProfile not set");
		}
		
		if(processedTiming.getTotalWorkingHours()!=null){
			Duration dayBalance = processedTiming.getTotalWorkingHours().minus(rawTiming.getTimingProfile().getDailyWorkHours());
			processedTiming.setDayBalance(dayBalance);
		}
		
	}

	private RawTimingProcessResult getProcessResultSkeltonForRawTiming(RawTiming rawTiming) {
		RawTimingProcessResult rawTimingProcessResult = new RawTimingProcessResult();
		rawTimingProcessResult.setRawTimingId(rawTiming.getId());
		rawTimingProcessResult.setEmployeeId(rawTiming.getEmployee().getId());
		rawTimingProcessResult.setDateOfWork(rawTiming.getInDateTime());
		if(rawTiming.getTimingProfile()!=null){
			rawTimingProcessResult.setTimingProfileId(rawTiming.getTimingProfile().getId());
		}
		return rawTimingProcessResult;
	}

	private void setOvertimeCalculations(RawTiming rawTiming,ProcessedTiming processedTiming) throws TmsBusinessException {
		if(rawTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		Duration totalOT= Duration.ZERO;
		
		if(rawTiming.getTimingProfile().getOvertimeAllowed()!=null && rawTiming.getTimingProfile().getOvertimeAllowed()){
			// TODO Introduce OT calculations
			//totalOT = calculateOT();
		}
		processedTiming.setTotalOT(totalOT);
		
	}
	
	private void setIdealTimeOut(RawTiming rawTiming,ProcessedTiming processedTiming) throws TmsBusinessException {
		TimingProfile timingProfile = rawTiming.getTimingProfile();
		if(timingProfile==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		
		Duration minimumBreakDuration = timingProfile.getMinBreakDuration();
		Duration idealWorkingHours = timingProfile.getDailyWorkHours();
		
		if(minimumBreakDuration==null){
			throw new TmsBusinessException("Minimum Break Duration in TimingProfile not set");
		}
		
		if(idealWorkingHours==null){
			throw new TmsBusinessException("DailyWorkingHours in TimingProfile not set");
		}
		
		if(rawTiming.getInDateTime()!=null){
			DateTime idealTimeOut= null;
			
			Duration totalDurationToBeInWork = idealWorkingHours.plus(minimumBreakDuration);
			if(processedTiming.getTotalBreakDuration()!=null){
				Duration overflownBreakHours = processedTiming.getTotalBreakDuration().minus(timingProfile.getMinBreakDuration());
				totalDurationToBeInWork= totalDurationToBeInWork.plus(overflownBreakHours);
			}
			idealTimeOut = rawTiming.getInDateTime().plus(totalDurationToBeInWork);
			
			processedTiming.setIdealTimeOut(idealTimeOut);
		}
	}

	private void setHourCalculations(RawTiming rawTiming,ProcessedTiming processedTiming) {
		if(rawTiming.getInDateTime()!=null && rawTiming.getOutDateTime()!=null && processedTiming.getEffectiveLunchDuration()!=null){
			Duration totalWorkHours = Duration.ZERO;
			totalWorkHours = new Duration(rawTiming.getInDateTime(),rawTiming.getOutDateTime());
			totalWorkHours = totalWorkHours.minus(processedTiming.getTotalBreakDuration());
			processedTiming.setTotalWorkingHours(totalWorkHours);
		}
	}

	private void setBreakCalculations(RawTiming rawTiming,ProcessedTiming processedTiming) throws TmsBusinessException{
		if(rawTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		List<BreakDetail> breakDetails = rawTiming.getBreakDetails();
		Duration lunchBreakDuration= Duration.ZERO;
		Duration otherBreaksDuration = Duration.ZERO;
		Duration totalBreakDuration = Duration.ZERO;
		if(breakDetails!=null){
			for(BreakDetail breakDetail : breakDetails){
				if(BreakType.LUNCH == breakDetail.getBreakType()){
					processedTiming.setLunchStart(breakDetail.getBreakStart());
					processedTiming.setLunchEnd(breakDetail.getBreakEnd());
					breakDetail = calculateAndUpdateBreakDetail(breakDetail, rawTiming.getTimingProfile());
					processedTiming.setActualLunchDuration(breakDetail.getActualBreakDuration()); 
					lunchBreakDuration = breakDetail.getEffectiveBreakDuration();
					processedTiming.setEffectiveLunchDuration(lunchBreakDuration);
				}else{
					otherBreaksDuration = otherBreaksDuration.plus(breakDetail.getEffectiveBreakDuration());
				}
			}
		}
		
		processedTiming.setOtherBreaksDuration(otherBreaksDuration);
		if(lunchBreakDuration!=null){
			totalBreakDuration = lunchBreakDuration.plus(otherBreaksDuration);
		}
		processedTiming.setTotalBreakDuration(totalBreakDuration);
		
	}
	
	@Override
	public BreakDetail calculateAndUpdateBreakDetail(BreakDetail breakDetail, TimingProfile timingProfile) throws TmsBusinessException {
		BreakDetail calculatedBreakDetail = breakDetail;
		
		if(timingProfile==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		
		if (timingProfile != null) {
			Duration effectiveBreakDuration = TMSUtils.calculateEffectiveBreakDuration(calculatedBreakDetail,timingProfile);
			Duration actualBreakDuration = TMSUtils.calculateActualBreakDuration(calculatedBreakDetail);
			breakDetail.setActualBreakDuration(actualBreakDuration);
			breakDetail.setEffectiveBreakDuration(effectiveBreakDuration);
			breakDetailRepository.save(breakDetail);
		}
		return calculatedBreakDetail;
	}

	
	public TimingsProcessDTO processRawTimingsForEmployee(Employee employee, DateTime startDate, DateTime endDate){
		//TODO
		TimingsProcessDTO timingsProcessDTO = null;
		return timingsProcessDTO;
	}
	
	public TimingsProcessDTO processRawTimings(DateTime startDate, DateTime endDate){
		//TODO
		TimingsProcessDTO timingsProcessDTO = null;
		return timingsProcessDTO;
	}
	

}
