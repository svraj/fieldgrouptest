package com.rnd.tms.services;

import org.joda.time.DateTime;

import com.rnd.tms.business.dto.TimingsProcessDTO;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.exceptions.TmsBusinessException;

public interface TimingsProcessingService {
	
	public TimingsProcessDTO processProcessedTiming(ProcessedTiming processedTiming) throws TmsBusinessException;
	
	public TimingsProcessDTO processProcessedTimingsForEmployee(Employee employee, DateTime startDate, DateTime endDate) throws TmsBusinessException;
	
	public TimingsProcessDTO processProcessedTimings(DateTime startDate, DateTime endDate) throws TmsBusinessException;

	public BreakDetail calculateAndUpdateBreakDetail(BreakDetail breakDetail,TimingProfile timingProfile) throws TmsBusinessException;;

}
