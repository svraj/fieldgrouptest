package com.rnd.tms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rnd.tms.data.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
