package com.rnd.tms.data.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@SuppressWarnings("serial")
@Entity
public class ProcessedTiming extends BaseEntity{
	
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private TimingProfile timingProfile;
	
	public TimingProfile getTimingProfile() {
		return timingProfile;
	}
	public void setTimingProfile(TimingProfile timingProfile) {
		this.timingProfile = timingProfile;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime inDateTime;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime outDateTime;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<BreakDetail> breakDetails;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserDetail approvedBy;
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserDetail processedBy;
	
	//private DateTime mainBreakStart;
	//private DateTime mainBreakEnd;
	private Duration mainBreakDuration;
	
	
	private Duration otherBreaksDuration;
	private Duration totalBreakDuration;
	
	private Duration totalWorkingHours;
	
	private DateTime idealTimeOut;
	
	private Duration dayBalance;
	private Duration totalOT;
	
	private String remarks;

	
	public ProcessedTiming(){}


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
	public Duration getMainBreakDuration() {
		return mainBreakDuration;
	}
	public void setMainBreakDuration(Duration mainBreakDuration) {
		this.mainBreakDuration = mainBreakDuration;
	}
	
	public List<BreakDetail> getBreakDetails() {
		return breakDetails;
	}
	public void setBreakDetails(List<BreakDetail> breakDetails) {
		this.breakDetails = breakDetails;
	}
	public DateTime getInDateTime() {
		return inDateTime;
	}
	public void setInDateTime(DateTime inDateTime) {
		this.inDateTime = inDateTime;
	}
	public DateTime getOutDateTime() {
		return outDateTime;
	}
	public void setOutDateTime(DateTime outDateTime) {
		this.outDateTime = outDateTime;
	}
	
	

}
