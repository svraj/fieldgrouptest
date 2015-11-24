package com.rnd.tms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rnd.tms.data.entity.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByCompanyNameStartsWithIgnoreCase(String companyName);
}
