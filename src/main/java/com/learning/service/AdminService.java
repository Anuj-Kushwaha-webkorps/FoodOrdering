package com.learning.service;

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
                .password(password) // TODO: Hash the password later
                .address(address)
                .phoneNumber(phone)
                .build();

        adminRepository.save(admin);
    }
}

