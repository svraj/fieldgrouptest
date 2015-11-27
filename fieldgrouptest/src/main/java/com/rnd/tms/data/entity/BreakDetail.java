package com.rnd.tms.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.rnd.tms.data.enums.BreakType;

@SuppressWarnings("serial")
@Entity
public class BreakDetail extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
	private BreakType breakType;
	
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime breakStart;
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime breakEnd;
	
	private Duration actualBreakDuration;
	private Duration effectiveBreakDuration;
	
	@ManyToOne
	@NotNull
	private ProcessedTiming processedTiming;
	
	public ProcessedTiming getProcessedTiming() {
		return processedTiming;
	}

	public void setProcessedTiming(ProcessedTiming processedTiming) {
		this.processedTiming = processedTiming;
	}

	public BreakDetail(BreakType breakType, DateTime breakStart,DateTime breakEnd) {
		super();
		this.breakType = breakType;
		this.breakStart = breakStart;
		this.breakEnd = breakEnd;
		//this.actualBreakDuration = actualBreakDuration;
		//this.effectiveBreakDuration = effectiveBreakDuration;
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
	public Duration getActualBreakDuration() {
		return actualBreakDuration;
	}
	public void setActualBreakDuration(Duration actualBreakDuration) {
		this.actualBreakDuration = actualBreakDuration;
	}

	public Duration getEffectiveBreakDuration() {
		return effectiveBreakDuration;
	}

	public void setEffectiveBreakDuration(Duration effectiveBreakDuration) {
		this.effectiveBreakDuration = effectiveBreakDuration;
	}

}
