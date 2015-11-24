package com.rnd.tms.data.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@SuppressWarnings("serial")
@Entity
public class ProcessedTiming extends BaseEntity{
	
	@OneToOne(cascade=CascadeType.ALL)
	private RawTiming rawTiming;
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Employee employee;
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserDetail approvedBy;
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserDetail processedBy;
	
	private LocalDate inDate;
	private LocalDate outDate;
	
	private LocalTime inTime;
	private LocalTime outTime;
	
	private DateTime lunchStart;
	private DateTime lunchEnd;
	
	private Duration actualLunchDuration;
	private Duration effectiveLunchDuration;
	
	private Duration otherBreaksDuration;
	private Duration totalBreakDuration;
	
	private Duration totalWorkingHours;
	
	private DateTime idealTimeOut;
	
	private Duration dayBalance;
	private Duration totalOT;
	
	private String remarks;

	public ProcessedTiming(RawTiming rawTiming) {
		this.rawTiming=rawTiming;
	}
	
	public ProcessedTiming(){}

	public RawTiming getRawTiming() {
		return rawTiming;
	}

	public void setRawTiming(RawTiming rawTiming) {
		this.rawTiming = rawTiming;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public UserDetail getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(UserDetail approvedBy) {
		this.approvedBy = approvedBy;
	}

	public UserDetail getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(UserDetail processedBy) {
		this.processedBy = processedBy;
	}

	public LocalDate getInDate() {
		return inDate;
	}

	public void setInDate(LocalDate inDate) {
		this.inDate = inDate;
	}

	public LocalDate getOutDate() {
		return outDate;
	}

	public void setOutDate(LocalDate outDate) {
		this.outDate = outDate;
	}

	public LocalTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalTime inTime) {
		this.inTime = inTime;
	}

	public LocalTime getOutTime() {
		return outTime;
	}

	public void setOutTime(LocalTime outTime) {
		this.outTime = outTime;
	}

	public DateTime getLunchStart() {
		return lunchStart;
	}

	public void setLunchStart(DateTime lunchStart) {
		this.lunchStart = lunchStart;
	}

	public DateTime getLunchEnd() {
		return lunchEnd;
	}

	public void setLunchEnd(DateTime lunchEnd) {
		this.lunchEnd = lunchEnd;
	}

	public Duration getActualLunchDuration() {
		return actualLunchDuration;
	}

	public void setActualLunchDuration(Duration actualLunchDuration) {
		this.actualLunchDuration = actualLunchDuration;
	}

	public Duration getEffectiveLunchDuration() {
		return effectiveLunchDuration;
	}

	public void setEffectiveLunchDuration(Duration effectiveLunchDuration) {
		this.effectiveLunchDuration = effectiveLunchDuration;
	}

	public Duration getOtherBreaksDuration() {
		return otherBreaksDuration;
	}

	public void setOtherBreaksDuration(Duration otherBreaksDuration) {
		this.otherBreaksDuration = otherBreaksDuration;
	}

	public Duration getTotalBreakDuration() {
		return totalBreakDuration;
	}

	public void setTotalBreakDuration(Duration totalBreakDuration) {
		this.totalBreakDuration = totalBreakDuration;
	}

	public Duration getTotalWorkingHours() {
		return totalWorkingHours;
	}

	public void setTotalWorkingHours(Duration totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}

	public DateTime getIdealTimeOut() {
		return idealTimeOut;
	}

	public void setIdealTimeOut(DateTime idealTimeOut) {
		this.idealTimeOut = idealTimeOut;
	}

	public Duration getDayBalance() {
		return dayBalance;
	}

	public void setDayBalance(Duration dayBalance) {
		this.dayBalance = dayBalance;
	}

	public Duration getTotalOT() {
		return totalOT;
	}

	public void setTotalOT(Duration totalOT) {
		this.totalOT = totalOT;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
