package com.learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	public Optional<Admin> findByEmail(String email);
}

