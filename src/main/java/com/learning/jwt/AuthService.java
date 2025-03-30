package com.learning.jwt;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.repository.AdminRepository;
import com.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password, String role) {
    	
    	if(role.equals("User")) {
    		User user = userRepository.findByEmail(email).orElse(null);
            if (user != null && password.equals(user.getPassword())) {
                return jwtUtil.generateToken(email, "USER");
            }
            throw new RuntimeException("Invalid credentials");
    	}
    	else {
            Admin admin = adminRepository.findByEmail(email).orElse(null);
            if (admin != null && password.equals(admin.getPassword())) {
                return jwtUtil.generateToken(email, "ADMIN");
            }
            throw new RuntimeException("Invalid credentials");
    	}
        
    }
}