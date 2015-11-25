package com.rnd.tms.services;

import org.joda.time.DateTime;

import com.rnd.tms.business.dto.TimingsProcessDTO;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.RawTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.exceptions.TmsBusinessException;

public interface TimingsProcessingService {
	
	public TimingsProcessDTO processRawTiming(RawTiming rawTiming) throws TmsBusinessException;
	
	public TimingsProcessDTO processRawTimingsForEmployee(Employee employee, DateTime startDate, DateTime endDate) throws TmsBusinessException;
	
	public TimingsProcessDTO processRawTimings(DateTime startDate, DateTime endDate) throws TmsBusinessException;

	public BreakDetail calculateAndUpdateBreakDetail(BreakDetail breakDetail,TimingProfile timingProfile) throws TmsBusinessException;;

}
