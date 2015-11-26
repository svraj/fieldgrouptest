package com.rnd.tms.data.entity;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@SuppressWarnings("serial")
@Entity
public class RawTiming extends BaseEntity implements Serializable{

	//@Column
	@ManyToOne
	private Employee employee;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime inDateTime;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime outDateTime;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="raw_timing_id")
	private List<BreakDetail> breakDetails;
	
	@OneToOne
	private TimingProfile timingProfile;
	
	public TimingProfile getTimingProfile() {
		return timingProfile;
	}

	public void setTimingProfile(TimingProfile timingProfile) {
		this.timingProfile = timingProfile;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
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
