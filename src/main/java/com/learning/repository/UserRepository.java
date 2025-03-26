package com.learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByName(String name);
}

