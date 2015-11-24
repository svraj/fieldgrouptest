package com.rnd.tms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rnd.tms.data.entity.RawTiming;


public interface RawTimingRepository extends JpaRepository<RawTiming, Long> {
	
}
