package com.rnd.tms.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.joda.time.Duration;

@Entity
public class TimingProfile extends BaseEntity{
	

	@OneToOne(cascade=CascadeType.ALL)
	private Client client;
	
	private String profileName;
	private Duration dailyWorkHours;
	private Duration minBreakDuration;
	private Duration maxHoursWithoutBreak;
	private Duration minBreakAfterMaxHoursWithoutBreak;
	private Boolean  overtimeAllowed;
	private String remarks;
	
	public TimingProfile(String profileName) {
		super();
		this.profileName=profileName;
		this.client = new Client();
	}
	
	public TimingProfile(){
		super();
		this.client = new Client();
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Duration getDailyWorkHours() {
		return dailyWorkHours;
	}
	public void setDailyWorkHours(Duration dailyWorkHours) {
		this.dailyWorkHours = dailyWorkHours;
	}
	public Duration getMinBreakDuration() {
		return minBreakDuration;
	}
	public void setMinBreakDuration(Duration minBreakDuration) {
		this.minBreakDuration = minBreakDuration;
	}
	public Duration getMaxHoursWithoutBreak() {
		return maxHoursWithoutBreak;
	}
	public void setMaxHoursWithoutBreak(Duration maxHoursWithoutBreak) {
		this.maxHoursWithoutBreak = maxHoursWithoutBreak;
	}
	public Duration getMinBreakAfterMaxHoursWithoutBreak() {
		return minBreakAfterMaxHoursWithoutBreak;
	}
	public void setMinBreakAfterMaxHoursWithoutBreak(Duration minBreakAfterMaxHoursWithoutBreak) {
		this.minBreakAfterMaxHoursWithoutBreak = minBreakAfterMaxHoursWithoutBreak;
	}
	public Boolean getOvertimeAllowed() {
		return overtimeAllowed;
	}
	public void setOvertimeAllowed(Boolean overtimeAllowed) {
		this.overtimeAllowed = overtimeAllowed;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
