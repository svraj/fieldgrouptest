package com.rnd.tms.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rnd.tms.data.entity.UserDetail;


public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
