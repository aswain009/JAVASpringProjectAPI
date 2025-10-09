package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}