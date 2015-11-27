package com.rnd.tms.business.dto;

import org.joda.time.DateTime;

import com.rnd.tms.business.enums.TimingRecordProcessStatus;


public class ProcessedTimingProcessResult {

	private Long employeeId;
	private DateTime dateOfWork;
	private Long timingProfileId;
	private Long processedTimingId;
	private TimingRecordProcessStatus processStatus;
	private String remarks;
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public DateTime getDateOfWork() {
		return dateOfWork;
	}
	public void setDateOfWork(DateTime dateOfWork) {
		this.dateOfWork = dateOfWork;
	}
	public Long getTimingProfileId() {
		return timingProfileId;
	}
	public void setTimingProfileId(Long timingProfileId) {
		this.timingProfileId = timingProfileId;
	}
	public Long getProcessedTimingId() {
		return processedTimingId;
	}
	public void setProcessedTimingId(Long processedTimingId) {
		this.processedTimingId = processedTimingId;
	}
	public TimingRecordProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(TimingRecordProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
