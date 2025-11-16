package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser_name(String user_name);
}