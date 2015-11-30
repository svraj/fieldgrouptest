package com.rnd.tms.data.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.joda.time.DateTime;
import org.joda.time.Duration;

@SuppressWarnings("serial")
@Embeddable
public class BreakDetail{
	
	public enum BreakType {
		BREAKFAST,LUNCH,TEA,DINNER,CASUAL,OFFICIAL,OTHER;
	}
	
	@Enumerated(EnumType.STRING)
	private BreakType breakType;
	
	private DateTime breakStart;
	private DateTime breakEnd;
	
	private Duration actual;
	private Duration effective;
	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BreakDetail(BreakType breakType, DateTime breakStart,DateTime breakEnd) {
		super();
		this.breakType = breakType;
		this.breakStart = breakStart;
		this.breakEnd = breakEnd;
	}
	
	public BreakDetail(){
		
	}
	
	public BreakType getBreakType() {
		return breakType;
	}
	public void setBreakType(BreakType breakType) {
		this.breakType = breakType;
	}
	public DateTime getBreakStart() {
		return breakStart;
	}
	public void setBreakStart(DateTime breakStart) {
		this.breakStart = breakStart;
	}
	public DateTime getBreakEnd() {
		return breakEnd;
	}
	public void setBreakEnd(DateTime breakEnd) {
		this.breakEnd = breakEnd;
	}
	public Duration getActual() {
		return actual;
	}
	public void setActual(Duration actual) {
		this.actual = actual;
	}

	public Duration getEffective() {
		return effective;
	}

	public void setEffective(Duration effective) {
		this.effective = effective;
	}

}
