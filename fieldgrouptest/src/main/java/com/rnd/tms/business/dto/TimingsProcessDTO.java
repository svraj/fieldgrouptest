package com.rnd.tms.business.dto;

import java.util.List;

import com.rnd.tms.business.enums.TimingRecordProcessStatus;

public class TimingsProcessDTO {

	private TimingRecordProcessStatus processStatus;
	private List<ProcessedTimingProcessResult> processedTimingProcessResults;
	
	public TimingRecordProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(TimingRecordProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public List<ProcessedTimingProcessResult> getProcessedTimingProcessResults() {
		return processedTimingProcessResults;
	}
	public void setProcessedTimingProcessResults(
			List<ProcessedTimingProcessResult> processedTimingProcessResults) {
		this.processedTimingProcessResults = processedTimingProcessResults;
	}
}
