package com.rnd.tms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rnd.tms.data.entity.ProcessedTiming;

public interface ProcessedTimingRepository extends JpaRepository<ProcessedTiming, Long> {
	
}
