package cm.rnd.tms.business.dto;

import org.joda.time.DateTime;

import cm.rnd.tms.business.enums.TimingRecordProcessStatus;


public class RawTimingProcessResult {

	private Long employeeId;
	private DateTime dateOfWork;
	private Long timingProfileId;
	private Long rawTimingId;
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
	public Long getRawTimingId() {
		return rawTimingId;
	}
	public void setRawTimingId(Long rawTimingId) {
		this.rawTimingId = rawTimingId;
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
