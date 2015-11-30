package com.rnd.tms.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.BreakDetail.BreakType;
import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;

public class TMSTestUtil {
	
	public static ProcessedTiming createProcessedTimingWithBreaks() {
		ProcessedTiming rawTiming = new ProcessedTiming();
		Employee employee = new Employee("Sajan","Raj");
		rawTiming.setEmployee(employee);
		DateTime startDateTime = new DateTime(2015,11,17,9,0,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(9).plusMinutes(30));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plusMinutes(30);
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.LUNCH,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(lunchBreakDetail);
		
		DateTime teaBreakStart = startDateTime.plusHours(5);
		DateTime teaBreakEnd = teaBreakStart.plusMinutes(40);
		BreakDetail teaBreakDetail = new BreakDetail(BreakType.LUNCH,teaBreakStart,teaBreakEnd);
		//teaBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(teaBreakDetail);
		rawTiming.setBreakDetails(breakDetails);
		rawTiming.setRecordCreatedDate(new Date());
		
		return rawTiming;
		
	}
	
	public static ProcessedTiming createProcessedTimingWithSingleLunchBreakWithDuration(Duration lunchBreakDuration) {
		ProcessedTiming rawTiming = new ProcessedTiming();
		Employee employee = new Employee("Ajay","Jadeja");
		rawTiming.setEmployee(employee);
		DateTime startDateTime = new DateTime(2015,11,17,9,0,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(9).plusMinutes(30));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plus(lunchBreakDuration);
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.LUNCH,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(lunchBreakDetail);
		
		
		rawTiming.setBreakDetails(breakDetails);
		rawTiming.setRecordCreatedDate(new Date());
		
		return rawTiming;
		
	}

	public static ProcessedTiming createProcessedTimingWithTimingProfileAndSingleLunchBreakWithDuration(Client client,Duration duration) {
		ProcessedTiming rawTiming = new ProcessedTiming();
		Employee employee = new Employee("Nayan","Mongia");
		rawTiming.setEmployee(employee);
		DateTime startDateTime = new DateTime(2015,11,18,8,30,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(8).plusMinutes(30));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plus(duration);
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.LUNCH,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(lunchBreakDetail);
		
		
		rawTiming.setBreakDetails(breakDetails);
		rawTiming.setRecordCreatedDate(new Date());
		
		rawTiming.setTimingProfile(getDummyTimingProfile(client));
		
		return rawTiming;
	}

	public static ProcessedTiming createProcessedTimingWithIncorrectTimingProfileNoBreakValues() {
		ProcessedTiming rawTiming = new ProcessedTiming();
		Employee employee = new Employee("Mohd","Azharuddin");
		rawTiming.setEmployee(employee);
		DateTime startDateTime = new DateTime(2015,11,18,8,30,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(8).plusMinutes(30));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plus(new Duration(10*60*1000));
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.LUNCH,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(lunchBreakDetail);
		
		rawTiming.setBreakDetails(breakDetails);
		rawTiming.setRecordCreatedDate(new Date());
		
		rawTiming.setTimingProfile(getDummyIncorrectTimingProfileNoBreakValues());
		
		return rawTiming;
	}
	
	public static ProcessedTiming createProcessedTimingWithIncorrectTimingProfileNoDailyHours() {
		ProcessedTiming rawTiming = new ProcessedTiming();
		Employee employee = new Employee("Mohd","Azharuddin");
		rawTiming.setEmployee(employee);
		DateTime startDateTime = new DateTime(2015,11,18,8,30,0);
		rawTiming.setInDateTime(startDateTime);
		rawTiming.setOutDateTime(startDateTime.plusHours(8).plusMinutes(30));
		
		List<BreakDetail> breakDetails = new ArrayList<BreakDetail>();
		
		DateTime lunchBreakStart = startDateTime.plusHours(3);
		DateTime lunchBreakEnd = lunchBreakStart.plus(new Duration(10*60*1000));
		BreakDetail lunchBreakDetail = new BreakDetail(BreakType.LUNCH,lunchBreakStart,lunchBreakEnd);
		//lunchBreakDetail.setProcessedTiming(rawTiming);
		breakDetails.add(lunchBreakDetail);
		
		rawTiming.setBreakDetails(breakDetails);
		rawTiming.setRecordCreatedDate(new Date());
		
		rawTiming.setTimingProfile(getDummyIncorrectTimingProfileNoDailyHours());
		
		return rawTiming;
	}
	

	public static TimingProfile getDummyTimingProfile(Client client) {
		TimingProfile timingProfile = new TimingProfile("Dummy Timing Profile");
		timingProfile.setRemarks("Test Timing profile Remarks");
		timingProfile.setClient(client);
		Duration dailyWorkHours = new Duration(8*60*60*1000);
		Duration maxHoursWithoutBreak = new Duration(4*60*60*1000);
		Duration minBreakAfterMaxHoursWithoutBreak = new Duration(30*60*1000);
		timingProfile.setDailyWorkHours(dailyWorkHours);
		timingProfile.setMinBreakDuration(dailyWorkHours);
		timingProfile.setMaxHoursWithoutBreak(maxHoursWithoutBreak);
		timingProfile.setMinBreakAfterMaxHoursWithoutBreak(minBreakAfterMaxHoursWithoutBreak);
		return timingProfile;
	}
	
	private static TimingProfile getDummyIncorrectTimingProfileNoDailyHours() {
		TimingProfile timingProfile = new TimingProfile("Dummy Incorrect Timing Profile");
		timingProfile.setClient(new Client("Test Client"));
		Duration dailyWorkHours = new Duration(8*60*60*1000);
		Duration maxHoursWithoutBreak = new Duration(4*60*60*1000);
		Duration minBreakAfterMaxHoursWithoutBreak = new Duration(30*60*1000);
		//timingProfile.setDailyWorkHours(dailyWorkHours);
		timingProfile.setMinBreakDuration(dailyWorkHours);
		timingProfile.setMaxHoursWithoutBreak(maxHoursWithoutBreak);
		timingProfile.setMinBreakAfterMaxHoursWithoutBreak(minBreakAfterMaxHoursWithoutBreak);
		return timingProfile;
	}
	
	private static TimingProfile getDummyIncorrectTimingProfileNoBreakValues() {
		TimingProfile timingProfile = new TimingProfile();
		timingProfile.setClient(new Client("Test Client"));
		Duration dailyWorkHours = new Duration(8*60*60*1000);
		Duration maxHoursWithoutBreak = new Duration(4*60*60*1000);
		Duration minBreakAfterMaxHoursWithoutBreak = new Duration(30*60*1000);
		timingProfile.setDailyWorkHours(dailyWorkHours);
		//timingProfile.setMinBreakDuration(dailyWorkHours);
		timingProfile.setMaxHoursWithoutBreak(maxHoursWithoutBreak);
		timingProfile.setMinBreakAfterMaxHoursWithoutBreak(minBreakAfterMaxHoursWithoutBreak);
		return timingProfile;
	}
	
	

	
	
	

}
