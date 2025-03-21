package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Admin;
import com.learning.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void saveAdmin(String name, String email, String password, String address, String phone) {
        Admin admin = Admin.builder()
                .adminId(java.util.UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password(password) 
                .address(address)
                .phoneNumber(phone)
                .build();

        adminRepository.save(admin);
    }
    
    public Admin authenticateAdmin(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent() && adminOptional.get().getPassword().equals(password)) {
            return adminOptional.get();
        }
        return null;
    }
}

