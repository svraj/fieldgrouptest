package com.rnd.tms.util;

import java.util.List;

import org.joda.time.Duration;

import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;

public class TMSUtils {

	public static Duration calculateActualBreakDuration(BreakDetail breakDetail) {
		Duration actualBreakDuration = null;
		if (breakDetail != null) {
			if (breakDetail.getBreakStart() != null && breakDetail.getBreakEnd() != null) {
				actualBreakDuration = new Duration(breakDetail.getBreakStart(), breakDetail.getBreakEnd());
			}
		}
		return actualBreakDuration;
	}

	public static Duration calculateEffectiveBreakDuration(BreakDetail breakDetail, TimingProfile timingProfile) {
		Duration effectiveBreakDuration = null;
		if (timingProfile != null) {
			Duration minimumBreakDuration = timingProfile.getMinBreakDuration();
			if (minimumBreakDuration != null) {
				Duration actualBreakDuration = calculateActualBreakDuration(breakDetail);
				if (actualBreakDuration != null) {
					if (actualBreakDuration.isShorterThan(minimumBreakDuration)) {
						effectiveBreakDuration = minimumBreakDuration;
					} else {
						effectiveBreakDuration = actualBreakDuration;
					}
				}
			}
		}
		return effectiveBreakDuration;
	}

	public static Duration calculateTotalBreakHours(ProcessedTiming processedTiming, TimingProfile timingProfile,
			boolean ignoreTimingProfile) {

		Duration totalBreakHours = null;
		List<BreakDetail> breaksTaken = null;

		if (processedTiming != null) {
			breaksTaken = processedTiming.getBreakDetails();
			if (ignoreTimingProfile) {
				if (breaksTaken != null) {
					totalBreakHours = calculateTotalBreakHours(breaksTaken);
				}
			} else {
				// TODO Include TimingProfile settings
			}

		}

		return totalBreakHours;
	}

	private static Duration calculateTotalBreakHours(List<BreakDetail> breaksTaken) {
		Duration totalBreakHours = null;
		if (breaksTaken != null) {
			for (BreakDetail breakTaken : breaksTaken) {
				totalBreakHours = new Duration(0);
				if (breakTaken.getEffectiveBreakDuration() != null) {
					totalBreakHours = totalBreakHours.plus(breakTaken.getEffectiveBreakDuration());
				}
			}
		}
		return totalBreakHours;
	}

	// TODO
	/*
	 * public static Duration calculateTotalWorkingHours(RawTiming rawTiming){
	 * Duration totalWorkingHours = null; if(rawTiming !=null){
	 * if(rawTiming.getInDateTime()!=null && rawTiming.getOutDateTime()!=null){
	 * totalWorkingHours = new
	 * Duration(rawTiming.getInDateTime(),rawTiming.getOutDateTime()); } }
	 * return totalWorkingHours; }
	 */

}
