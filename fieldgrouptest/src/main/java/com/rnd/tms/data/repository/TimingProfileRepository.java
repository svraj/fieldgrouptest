package com.rnd.tms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rnd.tms.data.entity.TimingProfile;

public interface TimingProfileRepository extends JpaRepository<TimingProfile, Long> {
}
