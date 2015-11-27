package com.rnd.tms.services;


import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rnd.tms.business.dto.ProcessedTimingProcessResult;
import com.rnd.tms.business.dto.TimingsProcessDTO;
import com.rnd.tms.business.enums.TimingRecordProcessStatus;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
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

	public TimingsProcessDTO processProcessedTiming(ProcessedTiming processedTiming){
		TimingsProcessDTO timingsProcessDTO = new TimingsProcessDTO();
		List<ProcessedTimingProcessResult> processedTimingProcessResults =  null;
		ProcessedTimingProcessResult processedTimingProcessResult = null;
		
		
		if(processedTiming.getInDateTime()!=null){
			processedTiming.setInDate(processedTiming.getInDateTime().toLocalDate());
			processedTiming.setInTime(processedTiming.getInDateTime().toLocalTime());
		}
		if(processedTiming.getOutDateTime()!=null){
			processedTiming.setOutDate(processedTiming.getOutDateTime().toLocalDate());
			processedTiming.setOutTime(processedTiming.getOutDateTime().toLocalTime());
		}
		
		try {
			setBreakCalculations(processedTiming);
			setHourCalculations(processedTiming);
			setIdealTimeOut(processedTiming);
			setOvertimeCalculations(processedTiming);
			setDayBalance(processedTiming);
			processedTimingRepository.save(processedTiming);
		} catch (TmsBusinessException e) {
			processedTimingProcessResult = getProcessResultSkeltonForProcessedTiming(processedTiming);
			timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.FAILED);
			processedTimingProcessResult.setProcessStatus(TimingRecordProcessStatus.FAILED);
			//	TODO Set proper message below
			processedTimingProcessResult.setRemarks(e.getMessage());
			e.printStackTrace();
			processedTimingProcessResults =  new ArrayList<ProcessedTimingProcessResult>();
			processedTimingProcessResults.add(processedTimingProcessResult);
		}catch(Exception e){
			processedTimingProcessResult = getProcessResultSkeltonForProcessedTiming(processedTiming);
			timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.FAILED);
			processedTimingProcessResult.setProcessStatus(TimingRecordProcessStatus.FAILED);
			processedTimingProcessResult.setRemarks(e.getMessage());
			e.printStackTrace();
			processedTimingProcessResults =  new ArrayList<ProcessedTimingProcessResult>();
			processedTimingProcessResults.add(processedTimingProcessResult);
		}
		
		
		
		timingsProcessDTO.setProcessedTimingProcessResults(processedTimingProcessResults);
		return timingsProcessDTO;
	}
	
	private void setDayBalance(ProcessedTiming processedTiming) throws TmsBusinessException {
		
		if(processedTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		
		if(processedTiming.getTimingProfile().getDailyWorkHours()==null){
			throw new TmsBusinessException("DailyWorkingHours in TimingProfile not set");
		}
		
		if(processedTiming.getTotalWorkingHours()!=null){
			Duration dayBalance = processedTiming.getTotalWorkingHours().minus(processedTiming.getTimingProfile().getDailyWorkHours());
			processedTiming.setDayBalance(dayBalance);
		}
		
	}

	private ProcessedTimingProcessResult getProcessResultSkeltonForProcessedTiming(ProcessedTiming processedTiming) {
		ProcessedTimingProcessResult processedTimingProcessResult = new ProcessedTimingProcessResult();
		processedTimingProcessResult.setProcessedTimingId(processedTiming.getId());
		processedTimingProcessResult.setEmployeeId(processedTiming.getEmployee().getId());
		processedTimingProcessResult.setDateOfWork(processedTiming.getInDateTime());
		if(processedTiming.getTimingProfile()!=null){
			processedTimingProcessResult.setTimingProfileId(processedTiming.getTimingProfile().getId());
		}
		return processedTimingProcessResult;
	}

	private void setOvertimeCalculations(ProcessedTiming processedTiming) throws TmsBusinessException {
		if(processedTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		Duration totalOT= Duration.ZERO;
		
		if(processedTiming.getTimingProfile().getOvertimeAllowed()!=null && processedTiming.getTimingProfile().getOvertimeAllowed()){
			// TODO Introduce OT calculations
			//totalOT = calculateOT();
		}
		processedTiming.setTotalOT(totalOT);
		
	}
	
	private void setIdealTimeOut(ProcessedTiming processedTiming) throws TmsBusinessException {
		TimingProfile timingProfile = processedTiming.getTimingProfile();
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
		
		if(processedTiming.getInDateTime()!=null){
			DateTime idealTimeOut= null;
			
			Duration totalDurationToBeInWork = idealWorkingHours.plus(minimumBreakDuration);
			if(processedTiming.getTotalBreakDuration()!=null){
				Duration overflownBreakHours = processedTiming.getTotalBreakDuration().minus(timingProfile.getMinBreakDuration());
				totalDurationToBeInWork= totalDurationToBeInWork.plus(overflownBreakHours);
			}
			idealTimeOut = processedTiming.getInDateTime().plus(totalDurationToBeInWork);
			
			processedTiming.setIdealTimeOut(idealTimeOut);
		}
	}

	private void setHourCalculations(ProcessedTiming processedTiming) {
		if(processedTiming.getInDateTime()!=null && processedTiming.getOutDateTime()!=null && processedTiming.getEffectiveLunchDuration()!=null){
			Duration totalWorkHours = Duration.ZERO;
			totalWorkHours = new Duration(processedTiming.getInDateTime(),processedTiming.getOutDateTime());
			totalWorkHours = totalWorkHours.minus(processedTiming.getTotalBreakDuration());
			processedTiming.setTotalWorkingHours(totalWorkHours);
		}
	}

	private void setBreakCalculations(ProcessedTiming processedTiming) throws TmsBusinessException{
		if(processedTiming.getTimingProfile()==null){
			throw new TmsBusinessException("TimingProfile must not be null");
		}
		List<BreakDetail> breakDetails = processedTiming.getBreakDetails();
		Duration lunchBreakDuration= Duration.ZERO;
		Duration otherBreaksDuration = Duration.ZERO;
		Duration totalBreakDuration = Duration.ZERO;
		if(breakDetails!=null){
			for(BreakDetail breakDetail : breakDetails){
				if(BreakType.LUNCH == breakDetail.getBreakType()){
					processedTiming.setLunchStart(breakDetail.getBreakStart());
					processedTiming.setLunchEnd(breakDetail.getBreakEnd());
					breakDetail = calculateAndUpdateBreakDetail(breakDetail, processedTiming.getTimingProfile());
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

	
	public TimingsProcessDTO processProcessedTimingsForEmployee(Employee employee, DateTime startDate, DateTime endDate){
		//TODO
		TimingsProcessDTO timingsProcessDTO = null;
		return timingsProcessDTO;
	}
	
	public TimingsProcessDTO processProcessedTimings(DateTime startDate, DateTime endDate){
		//TODO
		TimingsProcessDTO timingsProcessDTO = null;
		return timingsProcessDTO;
	}
	

}
