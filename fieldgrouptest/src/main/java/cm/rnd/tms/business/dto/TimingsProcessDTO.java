package cm.rnd.tms.business.dto;

import java.util.List;

import cm.rnd.tms.business.enums.TimingRecordProcessStatus;

public class TimingsProcessDTO {

	private TimingRecordProcessStatus processStatus;
	private List<RawTimingProcessResult> rawTimingProcessResults;
	
	public TimingRecordProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(TimingRecordProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public List<RawTimingProcessResult> getRawTimingProcessResults() {
		return rawTimingProcessResults;
	}
	public void setRawTimingProcessResults(
			List<RawTimingProcessResult> rawTimingProcessResults) {
		this.rawTimingProcessResults = rawTimingProcessResults;
	}
}
